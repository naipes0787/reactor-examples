package ar.com.naipes.reactorexamples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorExamples {

    Flux<String> emptyFlux() {
        return Flux.just();
    }

    Flux<String> fooBarFluxFromValues() {
        return Flux.just("foo", "bar");
    }

    Flux<String> fooBarFluxFromList() {
        List<String> list = new ArrayList<>();
        list.add("foo");
        list.add("bar");
        return Flux.fromIterable(list);
    }

    Flux<String> errorFlux() {
        IllegalStateException illegalStateException = new IllegalStateException();
        return Flux.error(illegalStateException);
    }

    Flux<Long> counter() {
        Duration duration = Duration.ofMillis(100);
        return Flux.interval(duration).take(10);
    }

    Mono<String> emptyMono() {
        return Mono.empty();
    }

    Mono<String> monoWithNoSignal() {
        return Mono.never();
    }

    Mono<String> fooMono() {
        return Mono.just("foo");
    }

    Mono<String> errorMono() {
        IllegalStateException illegalStateException = new IllegalStateException();
        return Mono.error(illegalStateException);
    }

    Mono<User> capitalizeOne(Mono<User> mono) {
        return mono.map(user ->
                new User(user.getUsername().toUpperCase(),
                        user.getFirstname().toUpperCase(),
                        user.getLastname().toUpperCase()
                ));
    }

    Flux<User> capitalizeManyUsers(Flux<User> flux) {
        return flux.map(t -> new User(
                t.getUsername().toUpperCase(),
                t.getFirstname().toUpperCase(),
                t.getLastname().toUpperCase()));
    }

    Flux<User> asyncCapitalizeMany(Flux<User> flux) {
        return flux.flatMap(this::asyncCapitalizeUser);
    }

    Mono<User> asyncCapitalizeUser(User user) {
        return Mono.just(new User(
                user.getUsername().toUpperCase(),
                user.getFirstname().toUpperCase(),
                user.getLastname().toUpperCase())
        );
    }

    User monoToValue(Mono<User> mono) {
        return mono.block();
    }

    Iterable<User> fluxToValues(Flux<User> flux) {
        return flux.toIterable();
    }

    Flux<User> mergeFluxWithInterleave(Flux<User> flux1, Flux<User> flux2) {
        return flux1.mergeWith(flux2);
    }

    Flux<User> mergeFluxWithNoInterleave(Flux<User> flux1, Flux<User> flux2) {
        return flux1.concatWith(flux2);
    }

    Flux<User> createFluxFromMultipleMono(Mono<User> mono1, Mono<User> mono2) {
        return mono1.concatWith(mono2);
    }

    Mono<User> betterCallSaulForBogusMono(Mono<User> mono) {
        return mono.onErrorReturn(User.SAUL);
    }

    Flux<User> betterCallSaulAndJesseForBogusFlux(Flux<User> flux) {
        return flux.onErrorResume(e -> Flux.just(User.SAUL, User.JESSE));
    }

    Flux<User> capitalizeManyBreakingBadUsers(Flux<User> flux) {
        return flux.map(
                u -> {
                    try {
                        return capitalizeUser(u);
                    } catch (GetOutOfHereException e) {
                        throw Exceptions.propagate(e);
                    }
                }
        );
    }

    User capitalizeUser(User user) throws GetOutOfHereException {
        if (user.equals(User.SAUL)) {
            throw new GetOutOfHereException();
        }
        return new User(user.getUsername(), user.getFirstname(), user.getLastname());
    }

    private static final class GetOutOfHereException extends Exception {

        private static final long serialVersionUID = 0L;
    }

}
