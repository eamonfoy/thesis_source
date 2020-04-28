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

    var data = {"OkPercent": 97.23781094527364, "KoPercent": 2.762189054726368};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.4068855721393035, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.40424, 500, 1500, "HTTP Request - Get Departure flights"], "isController": false}, {"data": [0.996, 500, 1500, "HTTP Request - Authenticate and get JWT token"], "isController": false}, {"data": [0.40364, 500, 1500, "HTTP Request - Get Arrival flights"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 25125, 694, 2.762189054726368, 1203.7771542288624, 0, 7930, 2190.0, 2533.0, 3315.0, 37.01804712673653, 249.65817873694425, 15.451368553518888], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["HTTP Request - Get Departure flights", 12500, 372, 2.976, 1208.1446400000063, 0, 7930, 2144.0, 2486.949999999999, 3190.99, 18.488990915249424, 124.47374265155057, 7.746540524909848], "isController": false}, {"data": ["HTTP Request - Authenticate and get JWT token", 125, 0, 0.0, 181.59199999999996, 148, 692, 205.4, 232.5999999999999, 608.5399999999984, 2.0629445645536615, 2.6411331290330566, 0.4593275007014011], "isController": false}, {"data": ["HTTP Request - Get Arrival flights", 12500, 322, 2.576, 1209.6315200000065, 2, 7896, 2128.8999999999996, 2441.0, 3167.959999999999, 18.482757804898966, 125.88200442227703, 7.7215070729817565], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["503\/Service Unavailable", 1, 0.1440922190201729, 0.003980099502487562], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 24, 3.4582132564841497, 0.0955223880597015], "isController": false}, {"data": ["500\/Internal Server Error", 659, 94.95677233429394, 2.6228855721393036], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 9, 1.2968299711815563, 0.03582089552238806], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 78)", 1, 0.1440922190201729, 0.003980099502487562], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 25125, 694, "500\/Internal Server Error", 659, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 24, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 9, "503\/Service Unavailable", 1, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 78)", 1], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["HTTP Request - Get Departure flights", 12500, 372, "500\/Internal Server Error", 344, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 23, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 5, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["HTTP Request - Get Arrival flights", 12500, 322, "500\/Internal Server Error", 315, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 4, "503\/Service Unavailable", 1, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 1, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 78)", 1], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
