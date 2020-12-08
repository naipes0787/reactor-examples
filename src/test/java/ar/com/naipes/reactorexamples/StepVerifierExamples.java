package ar.com.naipes.reactorexamples;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.function.Supplier;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class StepVerifierExamples {

    void expectFooBarComplete(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .verifyComplete();
    }

    void expectFooBarError(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("foo", "bar")
                .expectError(RuntimeException.class);
    }

    void expectSkylerJesseComplete(Flux<String> flux) {
        StepVerifier.create(flux)
                .assertNext(element -> {
                    assertThat(element).isEqualTo("swhite");
                })
                .assertNext(element -> {
                    assertThat(element).isEqualTo("jpinkman");
                });
    }

    void expect10Elements(Flux<Long> flux) {
        StepVerifier.create(flux)
                .expectNextCount(10);
    }

    void expect3600Elements(Supplier<Flux<Long>> supplier) {
        StepVerifier.withVirtualTime(supplier).expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .thenAwait(Duration.ofHours(1))
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    StepVerifier requestAllExpectFour(Flux<User> flux) {
        return StepVerifier.create(flux)
                .expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .expectNextCount(4)
                .expectComplete();
    }

    StepVerifier requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
        return StepVerifier.create(flux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(User.SKYLER)
                .thenRequest(1)
                .expectNext(User.JESSE)
                .thenCancel();
    }

}
