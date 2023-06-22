Laboratorium 7 - problem synchronizacji w node.js

Synchronizacja w node.js

W modelu współbieżności stosowanym w node.js nie ma problemu z synchronizacją dostępu wątków do sekcji krytycznej, natomiast wciąż aktualny jest problem wywołania callbacku we właściwym momencie (t.j. takim, w jakim warunki do jego wywołania są spełnione), np:
Po uruchomieniu kilkakrotnie poniższego kodu, czasem pierwszym wywołanym callbackiem będzie wypisanie na konsolę wartości (tutaj x=0), a czasem najpierw zostanie wywołana operacja podstawiania (x=1), a dopiero potem wypisanie. Zależy to od tego, kiedy poniższe callbacki zostaną wstawione do pętli zdarzeń:

var x = 0;
setTimeout(function() { x = 1 ; }, 2);
setTimeout(function() { console.log(x); }, 0);

Algorytm Binary Exponential Backoff (BEB)

Gdy chcemy asynchronicznie sprawdzić, czy można już wykonać pewną operację (czy warunek do wykonania tej operacji jest spełniony), można zastosować algorytm Binary Exponential Backoff , który polega na ponawianiu prób w coraz większych (o czynnik 2) odstępach czasu tak jak w poniższym szkielecie w pseudokodzie:

zaplanuj poniższy algorytm za odstęp_czasu=1 :

	if (warunek_spełniony) {
	    przestaw_odpowiednio_warunek_dla_następnych wywołań;
	    wykonaj_właściwą_operację; 
        }
	else { 
	    zaplanuj_powtórzenie_próby za 2*odstęp_czasu; 
	}

Jak widać, nie mamy mechanizmu synchronicznego oczekiwania na spelnienie sie warunku (tak jak np. wait() w javie), zamiast tego planujemy asynchroniczne powtórzenie sprawdzania warunku co pewien (wyliczany w algorytmie) czas.

Problem pięciu filozofów

Dla ćwiczenia w/w mechanizmu użyjemy problemu pieciu filozów.

Jest pięciu filozofów i pięć widelców
Każdy filozof zajmuje sie głownie myśleniem
Od czasu do czasu potrzebuje zjeść
Do jedzenia potrzebne mu są oba widelce po jego prawej i lewej stronie
Jedzenie trwa skończoną (ale nieokresloną z gory) ilość czasu, po czym filozof widelce odkłada i wraca do myślenia
Cykl powtarza sie od początku

Rozwiązania:

Rozwiazanie trywialne: każdy z filozofów podnosi najpierw prawy, a później lewy widelec. Rozwiązanie prowadzi do blokady, gdy wszyscy filozofowie podniosą prawy widelec i czekają na lewy.
Rozwiązanie asymetryczne - filozofowie z nieparzystym numerem najpierw podnoszą widelec lewy, z parzystym -- prawy.
Rozwiazanie z lokajem (inaczej: kelnerem), który pilnuje, aby o widelce w tym samym czasie rywalizowało tylko 4 filozofów, pozostały czeka. O lokaju można myśleć, jak o dodatkowym semaforze licznikowym z wartością maksymalną N-1, gdzie N - liczba filozofów.
Opis problemu

Obiektowość w javascript
Do zadania przydatna będzie podstawowa wiedza o obiektowości w języku javascript.

Zadanie

Zadanie polega na dokończeniu implementacji problemu pięciu filozofów w node.js. Wzięcie widelca przez filozofa jest możliwe tylko wtedy, jeśli jest on wolny (co zapisujemy za pomocą zmiennej stan).

Szkielet do zadania

Dokończ implementację funkcji podnoszenia widelca (Fork.acquire).

Zaimplementuj "naiwny" algorytm (każdy filozof podnosi najpierw lewy, potem prawy widelec, itd.). Zaobserwuj deadlock (zakleszczenie, blokadę).
Zaimplementuj rozwiązanie asymetryczne (poprawne): filozofowie z nieparzystym numerem najpierw podnoszą widelec lewy, z parzystym -- prawy.
Zaimplementuj rozwiązanie z lokajem (poprawne, opisane jako "Conductor solution" w wikipedii). Lokaj nalezy zaimplementować podobnie jak widelec, stan lokaja powinien pamietac liczbę filozofów dopuszczonych do rywalizacji o widelce.

Uruchom eksperymenty dla różnej liczby filozofów i dla poprawnych wariantów implementacji zmierz czas oczekiwania jako sumę milisekund, o jakie filozof musiał opóźniać wzięcie widelca i uzyskanie zgody lokaja w algorytmie BEB. Wyniki przedstaw na wykresach.