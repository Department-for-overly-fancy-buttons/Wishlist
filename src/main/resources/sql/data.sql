INSERT INTO Accounts (UserName, Password)
VALUES ("Freja Johannessen", "1235"),
       ("Ingor Slutspurt", "JEGERSAAGLADFORMINCYKEL");

INSERT INTO Wishlists (Title, OwnerID)
VALUES ("Bog Ønsker", 1),
       ("Julegave Ønsker", 1),
       ("Cykeludstyr", 2);

INSERT INTO Wishes (Name, Description, WishListId)
VALUES ("There is no antimemetics division", "Bog kan kun købes online, skal forudbestilles på saxo, bog og ide og lignde. Der er flere versioner, jeg tror de alle er ens untagen coveret", 1),
       ("Red rising", "Bog kan købes fysisk eller online", 1),
       ("Stormlight", "Bog kan købes fysisk eller online, er så gammel at man evt kan købe den billigt og brugt", 1),
       ("Nintendo switch joy cons", "Nintendo switch controllere, specifikt joy cons. Skal passe til en nintendo switch (1)", 2);

INSERT INTO Wishes (Name, Description, Url, WishListId)
VALUES ("MTG Commander deck pirates", "The Lost Caverns of Ixalan - Commander [EDH] Multiplayer Magic Deck: V1 - Ahoy Mateys - 100 kort", "https://www.kelz0r.dk/magic/the-lost-caverns-ixalan-commander-edh-multiplayer-magic-deck-ahoy-mateys-100-kort-p-299384.html?cmgo=1", 2),
       ("INNERGY+ Klikpedaler (KEO)", "Disse specifikke pedaler er kompatible med min nye cykel", "https://www.fribikeshop.dk/prod/12-in75030005/innergy-plus-klipedaler-keo", 3),
       ("Rawlink, Lappesæt m. multiværktøj, 16 dele", "Jeg mistede flere dele i swiez sidste år. Denne pakke har de fleste af dem jeg mistede", "https://www.silvan.dk/produkt/rawlink-lappesaet-m-multivaerktoej-16-dele-7400-1624908?gad_source=1&gad_campaignid=23151351997&gbraid=0AAAAAD96N0iMrXILTmjRxmdZ1CKh0K-H1&gclid=CjwKCAiAwqHIBhAEEiwAx9cTeSAz7QqofMpSTzRNsm5NOitwjQVAtiRVxDZQ8Whhkaz0M94ByStFbhoCm8kQAvD_BwE", 3),
       ("Cykel mobil holder", "Kunne være meget hjælpsom. Helst sort eller gul", "https://www.mobilcovers.dk/products/tech-protect-v2-universal-cykelholder-sort?tw_source=google&tw_adid=743208916716&tw_campaign=22400514604&tw_kwdid=pla-1201616251905&gad_source=1&gad_campaignid=22400514604&gbraid=0AAAAADjoXOMjAsNKAy29CspFZG7GgJ5Yx&gclid=CjwKCAiAwqHIBhAEEiwAx9cTeWTiu-rJi-_Y5ghpE_of2rpI3rRaASavAZ8MrQVcS4PeK2E4s3EPdBoCfQkQAvD_BwE", 3),
       ("Shimano SPD-SL Klampe", "Dette er den eneste butik hvor jeg har fundet nogen der passer", "https://cykelshoppen.dk/cykeludstyr/p/shimano-spd-sl-klampe-gul-6-grader", 3);

