extr.clear();

processPage('http://ceur-ws.org/Vol-1317/');
processPage('http://ceur-ws.org/Vol-1128/');
processPage('http://ceur-ws.org/Vol-1123/');
processPage('http://ceur-ws.org/Vol-1116/');
processPage('http://ceur-ws.org/Vol-1111/');
processPage('http://ceur-ws.org/Vol-1081/');
processPage('http://ceur-ws.org/Vol-1019/');
processPage('http://ceur-ws.org/Vol-1014/');
processPage('http://ceur-ws.org/Vol-1008/');
processPage('http://ceur-ws.org/Vol-996/');
processPage('http://ceur-ws.org/Vol-981/');
processPage('http://ceur-ws.org/Vol-979/');
processPage('http://ceur-ws.org/Vol-958/');
processPage('http://ceur-ws.org/Vol-951/');
processPage('http://ceur-ws.org/Vol-946/');
processPage('http://ceur-ws.org/Vol-943/');
processPage('http://ceur-ws.org/Vol-937/');
processPage('http://ceur-ws.org/Vol-936/');
processPage('http://ceur-ws.org/Vol-930/');
processPage('http://ceur-ws.org/Vol-929/');
processPage('http://ceur-ws.org/Vol-919/');
processPage('http://ceur-ws.org/Vol-914/');
processPage('http://ceur-ws.org/Vol-906/');
processPage('http://ceur-ws.org/Vol-905/');
processPage('http://ceur-ws.org/Vol-904/');
processPage('http://ceur-ws.org/Vol-903/');
processPage('http://ceur-ws.org/Vol-902/');
processPage('http://ceur-ws.org/Vol-901/');
processPage('http://ceur-ws.org/Vol-900/');
processPage('http://ceur-ws.org/Vol-895/');
processPage('http://ceur-ws.org/Vol-890/');
processPage('http://ceur-ws.org/Vol-875/');
processPage('http://ceur-ws.org/Vol-869/');
processPage('http://ceur-ws.org/Vol-862/');
processPage('http://ceur-ws.org/Vol-859/');
processPage('http://ceur-ws.org/Vol-856/');
processPage('http://ceur-ws.org/Vol-846/');
processPage('http://ceur-ws.org/Vol-843/');
processPage('http://ceur-ws.org/Vol-840/');
processPage('http://ceur-ws.org/Vol-839/');
processPage('http://ceur-ws.org/Vol-838/');
processPage('http://ceur-ws.org/Vol-830/');
processPage('http://ceur-ws.org/Vol-814/');
processPage('http://ceur-ws.org/Vol-813/');
processPage('http://ceur-ws.org/Vol-809/');
processPage('http://ceur-ws.org/Vol-800/');
processPage('http://ceur-ws.org/Vol-798/');
processPage('http://ceur-ws.org/Vol-784/');
processPage('http://ceur-ws.org/Vol-783/');
processPage('http://ceur-ws.org/Vol-782/');
processPage('http://ceur-ws.org/Vol-781/');
processPage('http://ceur-ws.org/Vol-779/');
processPage('http://ceur-ws.org/Vol-778/');
processPage('http://ceur-ws.org/Vol-775/');
processPage('http://ceur-ws.org/Vol-748/');
processPage('http://ceur-ws.org/Vol-745/');
processPage('http://ceur-ws.org/Vol-737/');
processPage('http://ceur-ws.org/Vol-736/');
processPage('http://ceur-ws.org/Vol-721/');
processPage('http://ceur-ws.org/Vol-718/');
processPage('http://ceur-ws.org/Vol-717/');
processPage('http://ceur-ws.org/Vol-689/');
processPage('http://ceur-ws.org/Vol-671/');
processPage('http://ceur-ws.org/Vol-669/');
processPage('http://ceur-ws.org/Vol-658/');
processPage('http://ceur-ws.org/Vol-628/');
processPage('http://ceur-ws.org/Vol-573/');
processPage('http://ceur-ws.org/Vol-571/');

extr.extractInstances(proc.areaTree.root);
extr.save('/tmp/train.arff');
