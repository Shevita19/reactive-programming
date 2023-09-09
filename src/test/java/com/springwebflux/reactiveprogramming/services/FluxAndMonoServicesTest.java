package com.springwebflux.reactiveprogramming.services;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class FluxAndMonoServicesTest {

    FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();

    @Test
    void streamFlux() {    //need to use reactiveTest here
        var streamFlux = fluxAndMonoServices.streamFlux();
        StepVerifier.create(streamFlux)
                .expectNext("blue", "purple", "green")
                .verifyComplete();
    }

    @Test
    void streamMono() {
        var streamMono = fluxAndMonoServices.streamMono();
        StepVerifier.create(streamMono)
                .expectNext("blue")
                .verifyComplete();
    }

    @Test
    void streamFluxMap() {
        var streamFluxMap = fluxAndMonoServices.streamFluxMap();
        StepVerifier.create(streamFluxMap)
                .expectNext("BLUE", "PURPLE", "GREEN")
                .verifyComplete();
    }

    @Test
    void stringFluxFilter() {
        var streamFluxFilter = fluxAndMonoServices.streamFluxFilter(4).log();
        StepVerifier.create(streamFluxFilter)
                .expectNext("purple", "green")
                .verifyComplete();
    }

    @Test
    void stringFluxFilterMap() {
        var streamFluxFilterMap = fluxAndMonoServices.streamFluxFilterMap(4).log();
        StepVerifier.create(streamFluxFilterMap)
                .expectNext("PURPLE", "GREEN")
                .verifyComplete();
    }

    @Test
    void streamFluxFlatMap() {
        var streamFluxFlatMap = fluxAndMonoServices.streamFluxFlatMap();
        StepVerifier.create(streamFluxFlatMap)
                .expectNextCount(15)  //15 elements will emited rather than 3, it is async
                .verifyComplete();
    }

    @Test
    void streamFluxFlatMapAsync() {
        var streamFluxFlatMapAsync = fluxAndMonoServices.streamFluxFlatMapAsync();
        StepVerifier.create(streamFluxFlatMapAsync)
                .expectNextCount(15)  //15 elements will emited rather than 3, it is async
                .verifyComplete();
    }

    @Test
    void streamMonoFlatMap() {
        var streamMonoFlatMap = fluxAndMonoServices.streamMonoFlatMap();
        StepVerifier.create(streamMonoFlatMap)
                .expectNextCount(1)  //it will emit one element as a list [b,l,u,e]
                .verifyComplete();
    }

    @Test
    void streamFluxConcatMap() {
        var streamFluxConcatMap = fluxAndMonoServices.streamFluxConcatMap();
        StepVerifier.create(streamFluxConcatMap)
                .expectNextCount(15)
                .verifyComplete();
    }

    @Test
    void streamMonoFlatMapMany() {
        var streamMonoFlatMapMany = fluxAndMonoServices.streamMonoFlatMapMany();
        StepVerifier.create(streamMonoFlatMapMany)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void streamFluxTransform() {
        var streamFluxTransform = fluxAndMonoServices.streamFluxTransform(20).log();  //will give error
        StepVerifier.create(streamFluxTransform)
                .expectNext("Purple", "Green")
                .verifyComplete();
    }

    @Test
    void streamFluxTransformDefaultIfEmpty() {
        var streamFluxTransformDefault = fluxAndMonoServices.streamFluxTransformDefaultIfEmpty(10);
        StepVerifier.create(streamFluxTransformDefault)
                .expectNext("Default")  //will give onNext(Default)
                .verifyComplete();
    }

    @Test
    void streamFluxTransformSwitchIfEmpty() {
        var streamFluxTransformSwitchIfEmpty
                = fluxAndMonoServices.streamFluxTransformSwitchIfEmpty(7);
        StepVerifier.create(streamFluxTransformSwitchIfEmpty)
                .expectNext("lavender")
                .verifyComplete();
    }

    @Test
    void streamFluxConcat() {
        var colorFlux = fluxAndMonoServices.streamFluxConcat().log();
        StepVerifier.create(colorFlux)
                .expectNext("purple", "white", "java", "python")
                .verifyComplete();

    }

    @Test
    void streamFluxConcatWith() {
        var colorFlux = fluxAndMonoServices.streamFluxConcatWith().log();
        StepVerifier.create(colorFlux)
                .expectNext("purple", "white", "java", "python")
                .verifyComplete();

    }

    @Test
    void streamMonoConcatWith() {
        var colorFlux = fluxAndMonoServices.streamMonoConcatWith().log();
        StepVerifier.create(colorFlux)
                .expectNext("purple", "java")
                .verifyComplete();


    }

    @Test
    void streamFluxMerge() {
        var colorFlux = fluxAndMonoServices.streamFluxMerge().log();
        StepVerifier.create(colorFlux)
                .expectNext("Purple", "Java", "White", "Python")
                .verifyComplete();
    }

    @Test
    void streamFluxMergeWith() {
        var colorFlux = fluxAndMonoServices.streamFluxMergeWith().log();
        StepVerifier.create(colorFlux)
                .expectNext("Purple", "Java", "White", "Python")
                .verifyComplete();
    }

    @Test
    void streamFluxMergeWithSequential() {
        var colorFlux = fluxAndMonoServices.streamFluxMergeWithSequential().log();
        StepVerifier.create(colorFlux)
                .expectNext("Purple", "White", "Java", "Python") //sequential order
                .verifyComplete();
    }


}



