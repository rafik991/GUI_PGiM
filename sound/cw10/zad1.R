install.packages('seewave')
library(seewave)
library(tuneR)
library(audio) 
len <- 500
cs = 2*cos(2*pi*(1:500)/50 + .6*pi)
w = rnorm(len,0,1)
par(mfrow=c(3,1))
plot.ts(cs+10, ylim=c(-3,3), main=" 2cos(10) 2cos(20) + 10 % salt and paper noise")
plot.ts( filter(cs+10, sides=2, rep(1/3,3)) , ylim=c(-3,3),  main="moving average")
plot.ts( runmed(cs+100, k <- 3), ylim=c(-3,3), main="median filter")