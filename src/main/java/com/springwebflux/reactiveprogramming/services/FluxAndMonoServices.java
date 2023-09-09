package com.springwebflux.reactiveprogramming.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoServices {
    public Flux<String> streamFlux() {    //from list we are creating flux
        return Flux.fromIterable(List.of("blue", "purple", "green")).log();
    }

    public Flux<String> streamFluxMap() {        //from list we are creating flux
        return Flux.fromIterable(List.of("blue", "purple", "green"))
                .map(String::toUpperCase)       //map will convert the data u r getting into different type of data but
                // same type of flux to convert it in uppercase
                .log();         //take list and will convert all values to upper case, first onNext call, it will
        // emit this data and will go to operator available and then operation perform
    }

    public Flux<String> streamFluxFilter(int number) {
        return Flux.fromIterable(List.of("blue", "purple", "green"))
                .filter(s -> s.length() > number);
    }

    public Flux<String> streamFluxFilterMap(int number) {
        return Flux.fromIterable(List.of("blue", "purple", "green"))
                .filter(s -> s.length() > number)
                .map(String::toUpperCase);
    }

    public Flux<String> streamFluxFlatMap() {
        return Flux.fromIterable(List.of("blue", "purple", "green"))
                .flatMap(s -> Flux.just(s.split("")))
                .log();
    }

    public Flux<String> streamFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("blue", "purple", "green"))
                .flatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(
                        new Random().nextInt(1000)
                ))
                .log();
    }

    public Flux<String> streamFluxConcatMap() {
        return Flux.fromIterable(List.of("blue", "purple", "green"))
                .concatMap(s -> Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(
                        new Random().nextInt(1000)
                ))
                .log();
    }


    public Mono<String> streamMono() {
        return Mono.just("blue").log();  // just coz only one element
    }

    public Mono<List<String>> streamMonoFlatMap() {  //convert mono of string to mono of list of string, mono will store one object
        return Mono.just("blue")
                .flatMap(s -> Mono.just(List.of(s.split(""))))  //having only one element but internally having the list
                .log();  // just coz only one element
    }

    public Flux<String> streamMonoFlatMapMany() {  //when we need to use flat map on mono and convert mono to flux
        return Mono.just("blue")
                .flatMapMany(s -> Flux.just(s.split("")))  //we can deal with array so no need for list
                .log();
    }

    public Flux<String> streamFluxTransform(int number) {  //when we need to use flat map on mono and convert mono to flux

        Function<Flux<String>, Flux<String>> filterData  //instead of enture filter, added filter in a new variable,
                // created functional interface and passing i/p as flux of string
                // and filter data as variable
                = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Blue", "Purple", "Green"))
                .transform(filterData);
        // .filter(s-> s.length() > number);
    }

    public Flux<String> streamFluxTransformDefaultIfEmpty(int number) {  //when we need to use flat map on mono and convert mono to flux

        Function<Flux<String>, Flux<String>> filterData
                = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Blue", "Purple", "Green"))
                .transform(filterData)
                .defaultIfEmpty("Default")         //if there is no data to be emitted, so we use defaultIfEmpty
                .log();
    }

    public Flux<String> streamFluxTransformSwitchIfEmpty(int number) {
        //switchIfEmpty use to switch to new set of data
        Function<Flux<String>, Flux<String>> filterData
                = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Blue", "Purple", "Green"))
                .transform(filterData)
                .switchIfEmpty(Flux.just("lavender")
                        .transform(filterData))
                .log();
    }

    public Flux<String> streamFluxConcat() {
        var colorFlux = Flux.just("purple", "white");
        var subjectFlux = Flux.just("java", "python");  // now concat two flux and  it is in sequential order

        return Flux.concat(colorFlux, subjectFlux);      //concat is static method
    }

    public Flux<String> streamFluxConcatWith() {
        var colorFlux = Flux.just("purple", "white");
        var subjectFlux = Flux.just("java", "python");

        return colorFlux.concatWith(subjectFlux);    //concatWith is instance method
    }

    public Flux<String> streamMonoConcatWith() {
        var colorFlux = Mono.just("purple");  //we can concatWith 2 monos and will get flux
        var subjectFlux = Mono.just("java");

        return colorFlux.concatWith(subjectFlux);
    }

    public Flux<String> streamFluxMerge() {
        var colorFlux = Flux.just("Purple", "White")
                .delayElements(Duration.ofMillis(60));
        var subjectFlux = Flux.just("Java", "Python")
                .delayElements(Duration.ofMillis(90));
        return Flux.merge(colorFlux, subjectFlux);    // merge will display data in non sequential way
    }

    public Flux<String> streamFluxMergeWith() {
        var colorFlux = Flux.just("Purple", "White")
                .delayElements(Duration.ofMillis(60));
        var subjectFlux = Flux.just("Java", "Python")
                .delayElements(Duration.ofMillis(90));
        return colorFlux.mergeWith(subjectFlux);    // mergeWith subscribe to publisher very eagerly and
        // it won't wait for any publisher to complete it
    }

    public Flux<String> streamFluxMergeWithSequential() {
        //if u want to merge data sequentially then use mergeWithSequentially
        var colorFlux = Flux.just("Purple", "White")
                .delayElements(Duration.ofMillis(60));
        var subjectFlux = Flux.just("Java", "Python")
                .delayElements(Duration.ofMillis(90));
        return Flux.mergeSequential(colorFlux, subjectFlux);    // will subscribe to both publishers but will be emiting
        // data in sequential manner
    }


    public static void main(String[] args) {
        FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();
        //to run any flux or mono, we need to subscribe to it and then we can get data
        fluxAndMonoServices.streamFlux()
                .subscribe(s -> {
                    System.out.println("colors- " + s);
                });

        fluxAndMonoServices.streamMono()
                .subscribe(s -> {
                    System.out.println("mono color- " + s);
                });
    }

}
