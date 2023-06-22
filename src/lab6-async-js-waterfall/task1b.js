const async = require('C:/Users/tekie/AppData/Roaming/npm/node_modules/async')

function printAsync(s, cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function () {
        console.log(s);
        if (cb) cb();
    }, delay);
}

function task1(cb) {
    printAsync("1", cb);
}

function task2(cb) {
    printAsync("2", cb);
}

function task3(cb) {
    printAsync("3", cb);
}

function loop(n) {
    if (n != 0) {
        async.waterfall([
            task1, task2, task3
        ], function (err, res) {
            loop(n - 1);
        })
    } else {
        console.log("done!")
    }
}

loop(4)