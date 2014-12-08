"use strict"

var Display = function() {
    // used for display logic

    var list = document.getElementById('list');

    var process = function(msg, repo, committer) {
        //do more stuff here
        log(msg, 'INFO');
    }

    return {
        newLog: function(msg, repo, committer) {
            process(msg, repo, committer);
        }
    }
}
var Ws = function(uri) {

    var onOpen = function(evt) {
        log('connection opened', 'INFO');
    }
    var onClose = function(evt) {
        log('connection closed', 'INFO');
    }
    var onMessage = function(evt) {
        log('message recieved', 'INFO');
        var data = JSON.parse(evt.data);
        log(JSON.stringify(data), 'DEBUG');
        d.newLog(data.msg, data.repo, data.committer);
    }
    var onError = function(evt) {
        log('connection error', 'ERROR');
    }

    var d = Display();
    var ws = new WebSocket(uri);
    ws.onopen = function(evt ) { onOpen(evt); }
    ws.onclose = function(evt ) { onClose(evt); }
    ws.onmessage = function(evt ) { onMessage(evt); }
    ws.onerror = function(evt ) { onError(evt); }


    return {
        send: function(msg) {
            ws.send(msg);
        }
    }
}

var wsUri = 'ws://188.226.165.41:9000/socket';
var w = Ws(wsUri);

