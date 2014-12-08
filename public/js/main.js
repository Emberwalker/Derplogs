"use strict"

var Display = function() {
    // used for display logic

    var list = $('#list');

    var process = function(msg, repo, committer) {
        //do more stuff here
        log(msg, 'INFO');
        var container = $('<li>');
        var row_div = $('<div>').addClass('row');
        var inner_div = $('<div>').addClass('col-md-12');
        var blockquote = $('<blockquote>');
        var strong = $('<strong>').append(committer).append($('<br>'));
        var em = $('<em>').append(msg);
        var code = $('<code>').css('float','right').append(repo);
        container.append(row_div.append(inner_div.append(blockquote.append(strong, em, code))));
        container.hide().prependTo(list).show('slow');
    }

    return {
        newLog: function(msg, repo, committer) {
            process(msg, repo, committer);
        }
    }
}
var Stack = function() {
    var d = Display();
    var stack = [];

    var _push = function(obj) {
        stack.unshift(obj);
    }

    var _pop = function() {
        if (stack.length !== 0) {
            var tmp = stack.pop();
            d.newLog(tmp.msg, tmp.repo, tmp.committer);
        }
    }

    setInterval(_pop, 1000);

    return {
        push: function(obj) {
            _push(obj);
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
        s.push(data);
    }
    var onError = function(evt) {
        log('connection error', 'ERROR');
    }

    var s = Stack();
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

var x;
window.onload = function() {
    console.log('done');
    var wsUri = 'ws://188.226.165.41:9000/socket';
    var w = Ws(wsUri);
    x = w;
}
