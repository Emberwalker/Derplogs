"use strict"

var Ws = function(uri) {

    var onOpen = function() {
        console.log('connection opened');
    }
    var onClose = function() {
        console.log('connection closed');
    }
    var onMessage = function() {
        console.log('connection message');
    }
    var onError = function() {
        console.log('connection error');
    }

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

