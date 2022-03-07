Brukerhistorie:
    Som en utvikler ønsker jeg en bedre prosjektstruktur slik at programmet blir lettere å utvide.
        Akseptansekriterier:
            - Prosjektet har samme funksjonalitet som før
            - Prosjektstrukturen følger MVC-prinsippet
        Arbeidsoppgaver:
            - Opprette en model for å holde på spillogikken
            - Flytte funksjonalitet som ikke har med view å gjøre inn i modellen
    Som spiller ønsker jeg å ha forskjellige teksturer for forskjellige tilstander spilleren befinner seg i.
        Akseptansekriterier:
            - Forskjellige teksturer for de forksjellige tilstandene en spiller kan befinne seg i (Stå, gå, hoppe og falle).
            - Teksturene til spillerene må peke i samme retning som spilleren beveger seg i.
        Arbeidsoppgaver:
            - Lage/sette sammen en png-fil (64x64 px) med flere teksturer etter hverandre (alle vendt i samme retning)
            - Legge til funksjonalitet for å finne hvilken tilstand spilleren er i, samt retning spilleren beveger seg i.
            - Legge til funksjonalitet for å velge rett tekstur til rett tilstand.
    Som utvikler ønsker jeg et større spillbrett for å teste mer funksjonsalitet.
        Akseptansekriterier:
            - Det nye brettet inneholder poenggjenstander, mulighet for fiender og død
            - Brettet lastes inn og vises på skjermen
        Arbeidsoppgaver:
            - Lag nytt brett med overnevnt funksjonalitet
            - Last inn brett i TileMapHelper. Edit: Finn problem som hindrer lasting av brett
            - Exception handling for lasting av nytt brett