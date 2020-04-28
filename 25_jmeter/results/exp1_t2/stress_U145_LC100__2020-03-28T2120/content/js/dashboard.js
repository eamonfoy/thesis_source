/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 6;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 97.93446560301939, "KoPercent": 2.065534396980614};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.253937210499228, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.2522068965517241, 500, 1500, "HTTP Request - Get Departure flights"], "isController": false}, {"data": [0.9172413793103448, 500, 1500, "HTTP Request - Authenticate and get JWT token"], "isController": false}, {"data": [0.2490344827586207, 500, 1500, "HTTP Request - Get Arrival flights"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 29145, 602, 2.065534396980614, 1607.4230914393595, 0, 5290, 2586.0, 2843.0, 3441.9600000000064, 38.43703882477221, 252.39803004612244, 15.838069446312772], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["HTTP Request - Get Departure flights", 14500, 440, 3.0344827586206895, 1591.5941379310373, 0, 5149, 2596.0, 2869.949999999999, 3449.99, 19.21415433868856, 124.72590584385253, 7.88314773316045], "isController": false}, {"data": ["HTTP Request - Authenticate and get JWT token", 145, 0, 0.0, 316.0206896551724, 146, 2030, 831.8000000000003, 1019.8999999999996, 1778.8399999999956, 2.3940429606881635, 3.0650296108029123, 0.5330486279657239], "isController": false}, {"data": ["HTTP Request - Get Arrival flights", 14500, 162, 1.1172413793103448, 1636.166068965525, 2, 5290, 2602.0, 2887.949999999999, 3515.0, 19.22100577558084, 128.67637464159452, 7.990563563203306], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Percentile 1
            case 8:
            // Percentile 2
            case 9:
            // Percentile 3
            case 10:
            // Throughput
            case 11:
            // Kbytes/s
            case 12:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["503\/Service Unavailable", 1, 0.16611295681063123, 0.0034311202607651397], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 289, 48.006644518272424, 0.9915937553611254], "isController": false}, {"data": ["500\/Internal Server Error", 190, 31.561461794019934, 0.6519128495453765], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 113, 18.770764119601328, 0.3877165894664608], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 5,630)", 5, 0.8305647840531561, 0.0171556013038257], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 5,729)", 4, 0.6644518272425249, 0.013724481043060559], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 29145, 602, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 289, "500\/Internal Server Error", 190, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 113, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 5,630)", 5, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 5,729)", 4], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["HTTP Request - Get Departure flights", 14500, 440, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 282, "500\/Internal Server Error", 106, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 47, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 5,729)", 4, "503\/Service Unavailable", 1], "isController": false}, {"data": [], "isController": false}, {"data": ["HTTP Request - Get Arrival flights", 14500, 162, "500\/Internal Server Error", 84, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 66, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 7, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 5,630)", 5, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
