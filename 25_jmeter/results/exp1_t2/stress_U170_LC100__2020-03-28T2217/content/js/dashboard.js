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

    var data = {"OkPercent": 51.5891132572432, "KoPercent": 48.4108867427568};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.13301141352063214, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.13108823529411764, 500, 1500, "HTTP Request - Get Departure flights"], "isController": false}, {"data": [0.5235294117647059, 500, 1500, "HTTP Request - Authenticate and get JWT token"], "isController": false}, {"data": [0.1310294117647059, 500, 1500, "HTTP Request - Get Arrival flights"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 34170, 16542, 48.4108867427568, 1216.9116769095708, 0, 20576, 2212.0, 2737.9500000000007, 3616.980000000003, 49.89093202743782, 215.22492640056345, 10.77854691291645], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["HTTP Request - Get Departure flights", 17000, 8389, 49.34705882352941, 1028.0026470588246, 0, 5566, 3112.0, 3536.0, 4347.980000000003, 25.060365999274723, 106.89302901004552, 5.356408502208261], "isController": false}, {"data": ["HTTP Request - Authenticate and get JWT token", 170, 0, 0.0, 963.5235294117647, 160, 4519, 1788.8, 2203.6499999999955, 4258.429999999997, 2.7514768956866553, 3.5226427834425835, 0.6126335275552319], "isController": false}, {"data": ["HTTP Request - Get Arrival flights", 17000, 8153, 47.95882352941177, 1408.3545882353005, 0, 20576, 3200.0, 3805.8499999999967, 20005.0, 25.063949931074138, 110.09921684371153, 5.470909692376874], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 16390, 99.08112682867852, 47.96605209247878], "isController": false}, {"data": ["500\/Internal Server Error", 73, 0.4413009309636078, 0.21363769388352355], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 71, 0.4292104944988514, 0.20778460637986537], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 78)", 1, 0.0060452182323781885, 0.0029265437518290896], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 177)", 2, 0.012090436464756377, 0.005853087503658179], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 5,630)", 4, 0.024180872929512754, 0.011706175007316359], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 5,729)", 1, 0.0060452182323781885, 0.0029265437518290896], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 34170, 16542, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 16390, "500\/Internal Server Error", 73, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 71, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 5,630)", 4, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 177)", 2], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["HTTP Request - Get Departure flights", 17000, 8389, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 8307, "500\/Internal Server Error", 42, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 37, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 177)", 2, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,863; actual size: 5,729)", 1], "isController": false}, {"data": [], "isController": false}, {"data": ["HTTP Request - Get Arrival flights", 17000, 8153, "Non HTTP response code: org.apache.http.NoHttpResponseException\/Non HTTP response message: 10.0.0.23:81 failed to respond", 8083, "Non HTTP response code: org.apache.http.ConnectionClosedException\/Non HTTP response message: Premature end of chunk coded message body: closing chunk expected", 34, "500\/Internal Server Error", 31, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 5,630)", 4, "Non HTTP response code: org.apache.http.TruncatedChunkException\/Non HTTP response message: Truncated chunk (expected size: 5,823; actual size: 78)", 1], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
