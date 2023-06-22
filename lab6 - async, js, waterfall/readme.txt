Laboratorium 6: Wstęp do Node.js

Podstawy
Node.js to platforma zbudowana na silniku V8 JavaScript Engine wspierająca budowanie szybkich, skalowalnych aplikacji sieciowych. Podstawą Node.js jest zarządzanie zdarzeniami (event-driven) wywołanymi przez asynchroniczne operacje wejscia-wyjścia (non-blocking I/O).
Wykład wsprowadzający. Zalecane obejrzenie !

Instalacja, moduły
Strona domowa: http://nodejs.org
Rejestr modułów - NPM: http://npmjs.org
Instalacja potrzebnego modułu:

npm install <nazwa_modułu>
Motywacja powstania: wysoki koszt operacji I/O

Model przetwarzania Node.js

Everything runs in parallel except your code.
setTimeout(callback, delay[, arg][, ...]) szereguje wywołanie callbacku po zadanym czasie. Ponieważ nie można przewidzieć ile innych callbackow będzie zarejestrowane w pętli zdarzeń do wykonania do tego czasu, callback może nie wykonać się dokładnie w zadanym czasie (ale w miarę blisko tego czasu).
Obiekt process API oraz dodatkowe wyjaśnienie
Dzialanie process.nextTick()
Funkcje (w tym anonimowe) w javascript

Ćwiczenia

Zadanie 1a: Zaimplementuj funkcje loop, wg instrukcji w pliku z Rozwiazaniem 2.
Zadanie 1b: wykorzystaj funkcję waterfall biblioteki async.
Zadanie 2 Zaimplementuj funkcję inparallel wg instrukcji z pliku node2.js