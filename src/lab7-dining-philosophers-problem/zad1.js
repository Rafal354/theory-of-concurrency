const async = require('C:/Users/tekie/AppData/Roaming/npm/node_modules/async')
waterfall = async.waterfall

var sumCond = 0;
var sumAsym = 0;

function random(a, b) {
    return Math.random() * (b - a) + a;
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    return this;
}

var Fork = function() {
    this.state = 0;
    return this;
}

Fork.prototype.release = function() { 
    this.state = 0; 
}

Fork.prototype.acquire = function(cb) {
    function waiting(timeToWait, fork, cb) {
        console.log('waiting: ' + timeToWait);
        if (fork.state === 0) {
            fork.state = 1;
            sumAsym += timeToWait;
            sumCond += timeToWait;
            cb();
        } else {
            setTimeout(function() {
                waiting(timeToWait * 2, fork, cb)
            }, timeToWait);
        }
    };
    let initWaitTime = 1;
    let fork = this;
    setTimeout(function() {
        waiting(initWaitTime * 2, fork, cb)
    }, initWaitTime);
}

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    let activities = [];

    for (let i = 0; i < count; i++) {
        activities.push(function(cb) {
            console.log('id: ' + id + ' thinking...');
            setTimeout(cb, random(5, 10));
        });
        activities.push(function(cb) {
            console.log('id: ' + id + ' want 1 fork...');
            forks[f1].acquire(cb);
        });
        activities.push(function(cb) {
            console.log('id: ' + id + ' got 1 fork...');
            console.log('id: ' + id + ' want 2 fork...');
            forks[f2].acquire(cb);
        });
        activities.push(function(cb) {
            console.log('id: ' + id + ' got 2 fork...');
            console.log('id: ' + id + ' eating...');
            eating(id, forks[f1], forks[f2], cb);
        });
        activities.push(function(cb) {
            console.log('id: ' + id + ' done eating');
            cb();
        });
    }
    waterfall(activities, function(err, result) {
        console.log('id: ' + id + ' DONE!!!');
    });
    function eating(id, fork1, fork2, cb) {
        setTimeout(function() {
            fork1.release();
            fork2.release();
            cb();
        }, random(0, 5));
    };
    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    let activities = [];
    let forkNumber = 0;

    for (let i = 0; i < count; i++) {
        activities.push(function(cb) {
            // console.log('id: ' + id + ' thinking...');
            setTimeout(cb, random(0, 5));
        });
        activities.push(function(cb) {
            forkNumber = ((id % 2) + 1);
            // console.log('id: ' + id + ' want ' + forkNumber + ' fork...');
            if (id % 2 === 0) {
                forks[f1].acquire(cb); 
            } else {
                forks[f2].acquire(cb); 
            }
        });
        activities.push(function(cb) {
            // console.log('id: ' + id + ' got ' + forkNumber + ' fork');
            if (id % 2 === 0) {
                forkNumber = 2;
            } else {
                forkNumber = 1;
            }
            // console.log('id: ' + id + ' want ' + forkNumber + ' fork...');
            if (id % 2 === 0) {
                forks[f2].acquire(cb); 
            } else {
                forks[f1].acquire(cb); 
            }
        });
        activities.push(function(cb) {
            // console.log('id: ' + id + ' got ' + forkNumber + ' fork');
            // console.log('id: ' + id + ' eating...');
            eating(id, forks[f1], forks[f2], cb);
        });
        activities.push(function(cb) {
            // console.log('id: ' + id + ' done eating');
            cb();
        });
    }
    waterfall(activities, function(err, result) {
        // console.log('id: ' + id + ' DONE!!!');
        phil--;
        if (phil === 0) {
            console.log("Time Asym: ", sumAsym);
        }
    });
    function eating(id, fork1, fork2, cb) {
        setTimeout(function() {
            fork1.release();
            fork2.release();
            cb();
        }, random(0, 5));
    };
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

var Philosopher2 = function(id, forks, conductor) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id + 1) % forks.length;
    this.conductor = conductor;
    return this;
}

var Conductor = function(initState) {
    this.state = initState;
    return this;
};

Conductor.prototype.acquire = function(cb) {
    function waiting(timeToWait, conductor, cb) {
        if (conductor.state > 0) {
            conductor.state--;
            sumCond += timeToWait;
            cb();
        } else {
            setTimeout(function() {
                waiting(timeToWait * 2, conductor, cb)
            }, timeToWait);
        }
    };
    let initWaitTime = 1;
    let conductor = this;
    setTimeout(function() {
        waiting(initWaitTime * 2, conductor, cb)
    }, initWaitTime);
};

Conductor.prototype.release = function() {
    this.state++;
}

Philosopher2.prototype.startConductor = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        conductor = this.conductor;
        id = this.id;

    let activities = [];

    for (let i = 0; i < count; i++) {
        activities.push(function(cb) {
            // console.log('id: ' + id + ' thinking...');
            setTimeout(cb, random(0, 5));
        });
        activities.push(function(cb) {
            // console.log('id: ' + id + ' want access...');
            conductor.acquire(cb);
        });
        activities.push(function(cb) {
            // console.log('id: ' + id + ' got access');
            // console.log('id: ' + id + ' want 1 fork...');
            forks[f1].acquire(cb);
        });
        activities.push(function(cb) {
            // console.log('id: ' + id + ' got 1 fork');
            // console.log('id: ' + id + ' want 2 fork...');
            forks[f2].acquire(cb);
        });
        activities.push(function(cb) {
            // console.log('id: ' + id + ' got 2 fork');
            // console.log('id: ' + id + ' eating...');
            eating(id, forks[f1], forks[f2], conductor, cb);
        });
        activities.push(function(cb) {
            // console.log('id: ' + id + ' done eating');
            cb();
        });
    }
    waterfall(activities, function(err, result) {
        // console.log('id: ' + id + ' DONE!!!');
        phil--;
        if (phil === 0) {
            console.log("Time Conductor: ", sumCond);
        }
    });
    function eating(id, fork1, fork2, conductor, cb) {
        setTimeout(function() {
            fork1.release();
            fork2.release();
            conductor.release();
            cb();
        }, random(0, 5));
    };
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

function naive(rounds, N) {
    var forks = [];
    var philosophers = []
    for (var i = 0; i < N; i++) {
        forks.push(new Fork());
    }
    for (var i = 0; i < N; i++) {
        philosophers.push(new Philosopher(i, forks));
    }
    for (var i = 0; i < N; i++) {
        philosophers[i].startNaive(rounds);
    }
}

function asym(rounds, N) {
    console.log("-------- ASYM --------")
    console.log("ROUNDS: ", rounds, "N: ", Nk)
    sumAsym = 0;
    var forks = [];
    var philosophers = []
    for (var i = 0; i < N; i++) {
        forks.push(new Fork());
    }
    for (var i = 0; i < N; i++) {
        philosophers.push(new Philosopher(i, forks));
    }
    for (var i = 0; i < N; i++) {
        philosophers[i].startAsym(rounds);
    }
}

function cond(rounds, N) {
    console.log("-------- COND --------")
    console.log("ROUNDS:", rounds, "N:", Nk)
    sumCond = 0;
    var conductor = new Conductor(Nk - 1)
    var forks = [];
    var philosophers = []
    for (var i = 0; i < N; i++) {
        forks.push(new Fork());
    }
    for (var i = 0; i < N; i++) {
        philosophers.push(new Philosopher2(i, forks, conductor));
    }
    for (var i = 0; i < N; i++) {
        philosophers[i].startConductor(rounds);
    }
}



var rounds = 10;
var Nk = 5;
var phil = Nk;

// naive(rounds, Nk)
// people = [2, 5, 10, 15, 25]
// table = [1, 5, 10, 100, 500, 1000]


asym(rounds, Nk)
// cond(rounds, Nk)
