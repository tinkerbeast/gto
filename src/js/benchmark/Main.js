


load('base.js');
//load('richards.js');
//load('deltablue.js');
//load('crypto.js');
//load('raytrace.js');
//load('earley-boyer.js');
//load('regexp.js');
load('splay.js');
//load('navier-stokes.js');

var iteration = Number(arguments[0]);
for(var i = 0; i < iteration;i++)
{
    var bm = new Benchmark("Splay", SplayRun, SplaySetup, SplayTearDown);
    bm.Setup();
    bm.run();
    bm.TearDown();
}
