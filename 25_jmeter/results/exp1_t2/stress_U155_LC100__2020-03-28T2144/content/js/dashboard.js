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

    var data = {"OkPercent": 55.46140266409886, "KoPercent": 44.53859733590114};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.14285026480500723, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.1356774193548387, 500, 1500, "HTTP Request - Get Departure flights"], "isController": false}, {"data": [0.5967741935483871, 500, 1500, "HTTP Request - Authenticate and get JWT token"], "isController": false}, {"data": [0.14548387096774193, 500, 1500, "HTTP Request - Get Arrival flights"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 31155, 13876, 44.53859733590114, 1198.649879634086, 0, 20008, 2559.0, 2842.9500000000007, 5839.080000000147, 45.98428962353546, 204.02119775154094, 10.669013850636297], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["HTTP Request - Get Departure flights", 15500, 7066, 45.58709677419355, 1022.7118064516131, 0, 19456, 2736.0, 3135.949999999999, 4003.9699999999993, 23.061083603123215, 101.30293349769315, 5.289258286740026], "isController": false}, {"data": ["HTTP Request - Authenticate and get JWT token", 155, 0, 0.0, 795.3096774193547, 161, 3162, 1802.4, 2047.1999999999994, 2982.7999999999993, 2.492161749336763, 3.1906484896293916, 0.5548953895007637], "isController": false}, {"data": ["HTTP Request - Get Arrival flights", 15500, 6810, 43.935483870967744, 1378.6213548386995, 0, 20008, 2901.8999999999996, 3558.949999999999, 20004.0, 23.06036310401414, 104.05466244045219, 5.413734064173271], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 4,242)", 1, 0.007206687806284232, 0.003209757663296421], "isController": false}, {"data": ["503\/Service Unavailable", 1, 0.007206687806284232, 0.003209757663296421], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 13757, 99.14240415105218, 44.15663617396886], "isController": false}, {"data": ["500\/Internal Server Error", 47, 0.3387143268953589, 0.1508586101749318], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 64, 0.46122801960219084, 0.20542449045097094], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 5,729)", 2, 0.014413375612568464, 0.006419515326592842], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 5,630)", 4, 0.028826751225136928, 0.012839030653185684], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 31155, 13876, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 13757, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 64, "500\/Internal Server Error", 47, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 5,630)", 4, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 5,729)", 2], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["HTTP Request - Get Departure flights", 15500, 7066, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 6996, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 36, "500\/Internal Server Error", 31, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 5,729)", 2, "503\/Service Unavailable", 1], "isController": false}, {"data": [], "isController": false}, {"data": ["HTTP Request - Get Arrival flights", 15500, 6810, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 6761, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 28, "500\/Internal Server Error", 16, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 5,630)", 4, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 4,242)", 1], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
