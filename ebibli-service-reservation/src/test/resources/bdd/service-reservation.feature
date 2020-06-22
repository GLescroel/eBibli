#Cette feature traite les fonctionnalités du micro-service reservation
Feature: §Microservice - Gérer les reservations
  En tant client du microservice reservation,
  Je veux pouvoir rechercher, modifier, annuler des réservations des abonnées
  Afin de pouvoir gérer les réservations sur les ouvrages.

  Scenario Outline: recherche de toutes les réservations d'un ouvrage
    Given je recherche toutes les réservations pour l'ouvrage d'"<ouvrageId>"
    When j'interroge le microservice reservation pour récupérer la liste
    Then Les infos <rechercheAllReservationsByOuvrageResultat> de toutes les réservations du fichier json sont retournées
    Examples:
      | ouvrageId | rechercheAllReservationsByOuvrageResultat |
      | 2         | rechercheAllReservationsByOuvrageResultat |

  Scenario Outline: recherche de toutes les réservations d'un abonné
    Given je recherche toutes les réservations pour l'abonné d'"<abonneId>"
    When j'interroge le microservice reservation pour récupérer la liste
    Then Les infos <rechercheAllReservationsByAbonneResultat> de toutes les réservations du fichier json sont retournées
    Examples:
      | abonneId | rechercheAllReservationsByAbonneResultat |
      | 4        | rechercheAllReservationsByAbonneResultat |

  Scenario Outline: recherche de toutes les réservations à supprimer
    Given je recherche toutes les réservations à supprimer
    When j'interroge le microservice reservation pour récupérer la liste
    Then Les infos <rechercheAllReservationsToCancelResultat> de toutes les réservations du fichier json sont retournées
    When Le systeme annule ces réservations
    Then Les réservations sont supprimées
    Examples:
      | rechercheAllReservationsToCancelResultat |
      | rechercheAllReservationsToCancelResultat |

  Scenario Outline: Réservation puis annulation
    Given L'abonné d'id "<abonneIdNew>" réserve l'ouvrage d'id "<ouvrageId>"
    When le microservice reservation enregistre la demande
    Then la réservation de l'ouvrage d'id "<ouvrageId>" pour l'abonné d'id "<abonneIdNew>" est créée
    When L'abonné d'id "<abonneIdSuppression>" annule sa réservation de l'ouvrage d'id "<ouvrageId>"
    Then la réservation de l'ouvrage d'id "<ouvrageId>" pour l'abonné d'id "<abonneIdSuppression>" est supprimé
    Examples:
      | abonneIdNew | abonneIdSuppression | ouvrageId |
      | 2           | 4                   | 8         |

  Scenario Outline: Recherche de la prochaine réservation à affecter
    Given L'abonné d'id "<abonneIdNew>" réserve l'ouvrage d'id "<ouvrageId>"
    When le microservice reservation enregistre la demande
    Then la réservation de l'ouvrage d'id "<ouvrageId>" pour l'abonné d'id "<abonneIdNew>" est créée
    When Je recherche la prochaine réservation puor l'ouvrage "<ouvrageId>" à affecter au livre d'id "<livreId>"
    Then l'abonné d'id "<abonneIdNew>" a été notifié que sa réservation pour l'ouvrage "<ouvrageId>"est disponible"
    Examples:
      | abonneIdNew | ouvrageId | livreId |
      | 2           | 16        | 23      |
