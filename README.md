# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Jan Nicole Yao, S362049, s362049@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 så gikk jeg frem ved å kopiere Programkode 5.2 3 a). Jeg lagt til q etter verdi for parameteren for en ny
Node i linje 97 hvor det opprettes en ny node p. Deretter sjekker vi om p er rotnoden, hvis ikke så setter vi sin
forelder lik q.

I oppgave 2 så begynte jeg med å sjekke om verdi er null. Hvis det er det returnerer det 0 siden null finnes ikke i
treet. Deretter sjekker vi om treet er tom, hvis ikke lager vi en ny node p lik roten og initialisere en hjelpevaribel
for komparatoren. Så bruker vi samme while-løkka som i leggInn-metoden fra oppgave 1. Til slutt returnerer vi antall. 

I oppgave 3 så brukte jeg while-løkka med true tilstand som mener at den skal fortsetter helt til vi returnerer noe. Så
bruker vi if-else setninger får finne noden lengst til venstre som har ingen barn. Hvis p har venstre barn flytter vi 
p til venstre barnet, hvis p har høyre barn flytter vi p til høyre barn, ellers hvis p har ingen barn returnerer vi p.

I oppgave 4 tok jeg utgangspunkt fra førstPostorden-metode for å finne første noden p i postorden, men istedet for å
returnere p bruker vi break som hopper ut av while-løkka. Neste går vi gjennom treet i postorden helt til p er null ved
bruk av while-løkka. Vi utfører oppgaven og flytter p til neste noden i postorden ved hjelp av nestePostorden-metode.

I oppgave 5 brukte jeg en kø for å lage en ArrayList av nodene i nivåorden. Først flytter vi første noden i køen til
hjelpevariabelen temp og så legge til verdien av temp til array listet. Neste hvis temp har venstre og/eller høyre barn 
legger vi de til køen. Vi fortsetter på samme måte helt til køen er tom, altså vi har gått gjennom hele treet. For
deserialize metoden lager vi først en ny SBinTre med roten lik første verdi i listet. Deretter med en for-løkka går vi
gjennom hele listet og bruker leggInn-metode for å legge inn alle verdiene til treet.