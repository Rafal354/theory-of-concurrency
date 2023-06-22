Na zajeciach poznamy podstawowe wlasności sieci Petriego.

Do zajęc będziemy używac symulatora Pipe2. Jest napisany w javie i jego uruchomienie nie wymaga uprawnien administratora (jest dostępny pod win i linux).

Maszyna stanów. Prosty model maszyny stanów swiateł ulicznych przedstawia sieć na rysunku poniżej:


Stanami sa miejsca sieci, zaś znacznik pokazuje w jakim stanie aktualnie sie znajdujemy.

Ćwiczenia:

Narysować przyklad w symulatorze.

Sprawdzić własciwości sieci (ograniczoność, bezpieczenstwo i możliwy deadlock) w symulatorze Pipe w menu "State Space Analysis".

Wygenerować graf osiągalności "Reachability/Coverability Graph". Zaobserwować:

Jakie znakowania są osiagalne ?
Ile wynosi maksymalna liczba znaczników w każdym ze znakowań ? Jakie mozemy wyciągnac z tego wnioski n.t. ograniczoności i bezpieczenstwa?
Czy kazde przejście jest przedstawione jako krawedz w grafie ? Jaki z tego wniosek n.t. zywotnosci przejsc ?
Czy wychodzac od dowolnego wezla grafu (znakowania) mozna wykonac dowolne przejscie ? Jaki z tego wniosek n.t. zywotnosci sieci? Czy sa mozliwe zakleszczenia ?

Wykonać analizę niezmiennikow (wybrac w menu "Invariant Analisis").

wynik analizy niezmiennikow przejsc (T-invariants) pokazuje nam, ile razy trzeba odpalic dane przejscie (T), aby przeksztalcic znakowanie poczatkowege z powrotem do niego samego (wynik nie mowi nic o kolejnosci odpalen). Z wyniku mozemy m.in. wnioskowac o odwracalnosci sieci.
wynik analizy niezmiennikow miejsc (P-invariants) pokazuje nam zbiory miejsc, w ktorych laczna suma znacznikow sie nie zmienia. Pozwala to wnioskowac n.t. zachowawczosci sieci (czyli wlasnosci, gdzie suma znacznikow pozostaje stala) oraz o ograniczonosci miejsc.

Rozwiazania do zadan wraz z dyskusja nalezy zawrzec w sprawozdaniu

Zadanie 1 - wymyslic wlasna maszyne stanow (maszyna stanow jest modelowana przez sieć Petri, w której każda tranzycja ma dokładnie jedno miejsce wejściowe i jedno miejsce wyjściowe), zasymulowac przyklad i dokonac analizy grafu osiagalnosci oraz niezmiennikow j.w.
Zadanie 2 - zasymulowac siec jak ponizej.

Dokonac analizy niezmiennikow przejsc. Jaki wniosek mozna wyciagnac o odwracalnosci sieci ? Wygenerowac graf osiagalnosci. Prosze wywnioskowac z grafu, czy siec jest zywa. Prosze wywnioskowac czy jest ograniczona. Objasnic wniosek.

Zadanie 3 - zasymulowac wzajemne wykluczanie dwoch procesow na wspolnym zasobie. Dokonac analizy niezmiennikow miejsc oraz wyjasnic znaczenie rownan (P-invariant equations). Ktore rownanie pokazuje dzialanie ochrony sekcji krytycznej ?
Zadanie 4 - uruchomic problem producenta i konsumenta z ograniczonem buforem (mozna posluzyc sie przykladem, menu:file, examples). Dokonac analizy niezmiennikow. Czy siec jest zachowawcza ? Ktore rownanie mowi nam o rozmiarze bufora ?
Zadanie 5 - stworzyc symulacje problemu producenta i konsumenta z nieograniczonym buforem. Dokonac analizy niezmiennikow. Zaobserwowac brak pelnego pokrycia miejsc.
Zadanie 6 - zasymulowac prosty przyklad ilustrujacy zakleszczenie. Wygenerowac graf osiagalnosci i zaobserwowac znakowania, z ktoroch nie mozna wykonac przejsc. Zaobserwowac wlasciwosci sieci w "State Space Analysis". Ponizej przyklad sieci z mozliwoscia zakleszczenia (mozna wymyslic inny):
