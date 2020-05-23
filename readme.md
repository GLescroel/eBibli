eBibli [![Build Status](https://travis-ci.org/GLescroel/eBibli.svg?branch=master)](https://travis-ci.org/GLescroel/eBibli) 

Système d'information des bibliothèques de la ville à destination des usagers et des bibliothécaires.
Les clients frontend et batch interrogent les microservices via les API Rest exposées.


Pré-requis technique :

    Version de Java : 1.8
    JDK : jdk1.8.0_202
    Maven 3.6.0
    Base de données : PostgresSQL

Installation et déploiement:

    Packaging
    mvn clean package du root : les fichiers war de chaque application microservice sont générés :
    - ebibli-service-utilisateur-1.0.war
    - ebibli-service-bibliotheque-1.0.war
    - ebibli-service-ouvrage-1.0.war
    - ebibli-service-livre-1.0.war
    - ebibli-service-emprunt-1.0.war
    - ebibli-frontend-1.0.war
    - ebibli-batch-1.0.jar

Il est maintenant possible de lancer ces applications directement dans votre IDE en exécutant le Main
ou en ligne de commande (applications standalones intégrant un conteneur web grace à SpringBoot) : mvn clean install spring-boot:run
ou de déployer ces war dans un tomcat.

    Les port des applications sont paramétrés dans les fichiers application.propertie de chaque application : http://localhost:8081/
    - ebibli-service-utilisateur : 9003
    - ebibli-service-bibliotheque : 9004
    - ebibli-service-ouvrage-1.0 : 9001
    - ebibli-service-livre-1.0 : 9002
    - ebibli-service-emprunt-1.0 : 9005
    - ebibli-frontend-1.0 : 8080

    Les applications sont livrées avec 1 configuration
    •dev et prod avec une base de données PostgreSQL peuplée avec le contenu du script src\resources\data.sql présent dans le module model. 
    La base sera créée automatiquement au premier lancement.
    En prod, il faudra ensuite modifier le ddl-auto=update dans les fichiers application.properties pour qu'elle ne se recrée pas à chaque démarrage.

    Documentation : la javadoc peut être générée via la commande mvn javadoc:javadoc puis consultée à partir de la page \target\site\apidocs\index.html

    Un projet postman pour tester les API est présent dans le répertoire resources/postman

    Comptes utilisateurs en base :
    user@oc.com / USER = compte utilisateur
    membre@oc.com / MEMBRE = compte membre de l'association
    admin@oc.com / ADMIN = compte administrateur
    dupont@oc.com / DUPONT = utilisateur
    dubois@oc.com / DUBOIS = utilisateur
    smith@oc.com / SMITH = utilisateur

    Serveur SMTP pour le batch d'envoi d'emails :
    pour ce projet, un serveur local (port 25) smtp a été utilisé : fakeSMTP cf http://nilhcem.com/FakeSMTP/ 
    une fois téléchargé, se lance par 'java -jar fakeSMTP-2.0.jar'
    Le serveur smtp définitif devra être paramétré dans le fichier application.properties
 
Sources disponibles sur : https://github.com/GLescroel/ebibli
