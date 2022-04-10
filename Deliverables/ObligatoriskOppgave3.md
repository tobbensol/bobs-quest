# Obligatorisk oppgave 3:

### Prosjekt Plattformspill

## Team:

**Teamnavn:** Grabbane

**Prosjektnavn:** inf112.22v.2d-platformer

**Teammedlemmer:**

- Espen Lade Kalvatn

- Kristoffer Jensvoll-Johnsen

- Martin Andvik Øvsttun

- Tobias Soltvedt

**Gruppe:** Gruppe 1

## Deloppgave 1: Team og prosjekt

- Lag korte referat fra team-møtene (ha med dato, hvem som var tilstede, hva dere diskuterte, hvilke avgjørelser dere tok, og hva dere ble enige om å gjøre til neste gang)

- Link til [møte referater](src/main/resources/MeetingReports.md).

- Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt? (se spørsmål 2)

Rollene fungerer bra. Siden vi er et mindre team, ender det ofte opp med at alle gjør litt av alt, dermed glir rollene litt inn i hverandre, men de som står oppført som en rolle har et ekstra ansvar for å passe på at standarden er god. Vi har oppdatert hvem som er testere, nå er både Tobias og Martin testere. Tobias har hovedansvar for manuelle tester, mens Martin har hovedansvar for JUnit testing.

- Trenger dere andre roller? Skriv ned noen linjer om hva de ulike rollene faktisk innebærer for dere.

Nei, vi føler ikke vi trenger flere roller. Har skrevet hva rollene innebærer i andre innlevering.

Team/prosjekt-lead - Martin

Kundekontakt - Espen

Utviklere - Martin, Espen, Tobias, Kristoffer

Testing - Tobias, Martin

Ansvarlig for game design - Tobias, Kristoffer

- Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?

Vi har blitt litt flinkere til å oppdatere brettet. Dette varierer litt fra teammedlem til teammedlem. Vi har blitt flinkere på å fordele ukens arbeid tydelig på mandagsmøte. Vi har også blitt flinkere til å informere de andre hva vi jobber med og jobber helst to og to hvis vi er flere som arbeider.

- Hvordan er gruppedynamikken? Er det uenigheter som bør løses?

Gruppedynamikken er god og vi klarer å kommunisere bra. Det er ingen uenigheter utenfor hvordan man bør implementere kode. Ingen endringer siden forrige innlevering.

- Hvordan fungerer kommunikasjonen for dere?

Kommunikasjonen er god. Ingen endringer siden forrige innlevering.

- Gjør et kort retrospektiv hvor dere vurderer hva dere har klart til nå, og hva som kan forbedres. Dette skal handle om prosjektstruktur, ikke kode. Dere kan selvsagt diskutere kode, men dette handler ikke om feilretting, men om hvordan man jobber og kommuniserer.

Vi har klart å øke effektiviteten når vi arbeider sammen ved å begrense hvor mange som jobber sammen. Nå deler vi oss inn i grupper på to. Dette gjør det lettere å implementere løsninger og ulike grupper kan jobbe på forskjellige ting samtidig. Vi har minsket terskelen for å ta kontakt i Discord-kanalen, noe som gjør det lettere for alle å få oversikt over hva de andre teammedlemmene holder på med, og for å hjelpe hverandre når det trengs. Det er ikke alltid at en brukerhistorie blir skrevet før arbeidsoppgaven er påbegynt.

- Under vurdering vil det vektlegges at alle bidrar til kodebasen. Hvis det er stor forskjell i hvem som committer, må dere legge ved en kort forklaring for hvorfor det er sånn. Husk å committe alt. (Også designfiler)

Ingen endring siden andre innlevering. Antall commits er ganske jevnt fordelt, men vil variere litt etter forklaring i andre innlevering.

- Bli enige om maks tre forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint.

Vi har forbedret alle punktene nedenfor fra forrige innlevering. Vi føler at vi ikke har så mye mer konkret å forbedre, så vi tenker å fortsette med de samme punktene og forbedre dem enda mer.

1) Effektivisering av team programmering (diskutere, komme fram til løsning, implementere løsning). Bli enda flinkere på å jobbe i par og kommunisere arbeid til resten av teamet.

2) Mer jevn fordeling av arbeidsoppgaver.

3) Utvide Junit testing og skrive brukerhistorier før man jobber med noe.

## Deloppgave 2: krav

### **«Stretch goal»**

- Bestem dere for én litt mer avansert ting som dere vil prøve å få til utover et vanlig single-player plattformspill. Dette kan være f.eks.: multiplayer (på samme maskin eller over nett), å porte til en annen plattform (Android eller Web, f.eks.), å bytte ut grafikk-frontenden, e.l.

Samme strech goal som i andre innlevering. Her har fokuset vært på å forbedre kamerasystemet slik at alle spillere er på skjermen til enhver tid og kameraet oppfører seg bra for lokal multiplayer.

**MVP og annet**

- Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang. Er dere kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.

I denne perioden har vi fokusert på generalisering, utvidbarhet og generell fiksing av små problemer mens vi holder fokuset på kodekvalitet og prosjekt struktur. Hva vi har gjort kommer fram av møtereferat. Framover tenker vi å prioritere å få et mer komplett spill. Vi har en god "grunnmur", men trenger mer spill å spille.

- For hvert krav dere jobber med, må dere lage 1) ordentlige brukerhistorier, 2) akseptansekriterier og 3) arbeidsoppgaver. Husk at akseptansekriterier ofte skrives mer eller mindre som tester

Vi har skrevet en del [brukerhistorier](src/main/resources/UserStories.md).

- Dersom dere har oppgaver som dere skal til å starte med, hvor dere har oversikt over både brukerhistorie, akseptansekriterier og arbeidsoppgaver, kan dere ta med disse i innleveringen også.

Vi har ingen nye brukerhistorier som ikke er implementert.

- Har dere gjort justeringer på kravene som er med i MVP? Forklar i så fall hvorfor. Hvis det er gjort endringer i rekkefølge ut fra hva som er gitt fra kunde, hvorfor er dette gjort?

Ingen nye endringer. Endringer fra MVP er forklart i andre innlevering.

## Deloppgave 3: Produkt og kode

Evt. tekst / kommentarer til koden kan dere putte i en egen ## Kode-seksjon i Deliverables/obligX.md.

- _Utbedring av feil:_ hvis dere har rettet / forbedret noe som dere har fått trekk for tidligere, lag en liste med «Dette har vi fikset siden sist», så det er lett for gruppelederne å få oversikt.

    - Vi har ikke fått tilbakemelding på andre innlevering, så vi vet ikke om det er noe annet å forbedre enn fra første innlevering. Da er forbedringene nesten det samme som skrevet på andre innlevering.

    - Commitmeldingene har blitt mer beskrivende/utfyllende

    - Nytteverdien til brukerhistoriene har blitt oppdatert

    - Lagt til flere [manuelle tester](src/main/resources/BugReplication.md)

    - Laget JUnit tester.

    - Laget [README](README.md) som inneholder dokumentasjon om prosjektoppsett

- Lag og lever et klassediagram. (Hvis det er veldig mange klasser, lager dere for de viktigste.) Det er ikke nødvendig å ta med alle metoder og feltvariabler med mindre dere anser dem som viktige for helheten. (Eclipse har forskjellige verktøy for dette)

Link til [klassdiagram](src/main/resources/ClassDiagram.uml).

- Kodekvalitet og testdekning vektlegges. Dersom dere ikke har automatiske tester for GUI-et, lager dere manuelle tester som gruppelederne kan kjøre basert på akseptansekriteriene.

    - Har skrevet [manuelle tester](src/main/resources/BugReplication.md).
    - Har noen JUnit tester. Testdekning er noe vi ønsker å forbedre.

- Statiske analyseverktøy som SpotBugs eller SonarQube kan hjelpe med å finne feil dere ikke tenker på. Hvis dere prøver det, skriv en kort oppsummering av hva dere fant / om det var nyttig.

Vi har brukt spot-bugs, men noen bugs er litt vanskelige å fikse, vi må se på sonarQube.

- Automatiske tester skal dekke forretningslogikken i systemet (unit-tester). _Coverage_ kan hjelpe med å se hvor mye av koden som dekkes av testene – i Eclipse kan dette gjøres ved å installere _EclEmma_ gjennom Eclipse Marketplace.

Vi har startet enhetstesting. Nå som vi har fått dette til å fungere, skal det være lettere å skrive flere tester videre framover. Vi har foreløpig 28% line coverage i hele prosjektet. Det viktigste å teste er modellen (hvor logikken ligger). Her har vi 34% coverage. Diverse andre deler av koden kan fort bli vanskelig å teste automatisk.