"use strict"

var Ws = function(uri) {

    var onOpen = function() {
        console.log('connection opened');
    }

    var ws = new WebSocket(uri);
    ws.onopen = function(evt ) { onOpen(evt); }
    ws.onclose = function(evt ) { onClose(evt); }
    ws.onmessage = function(evt ) { onMessage(evt); }
    ws.onerror = function(evt ) { onError(evt); }


    return this;
}

var wsUri = 'ws://188.226.165.41:9000';
var w = Ws(wsUri);

