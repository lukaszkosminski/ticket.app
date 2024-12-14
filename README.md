# Mandaty - ticket.app

System "Mandaty" to aplikacja internetowa umożliwiająca zarządzanie mandatami zarówno dla pracowników fizycznych, jak i biurowych. Zapewnia szeroki zakres funkcjonalności, takich jak dodawanie mandatów, zarządzanie nimi oraz automatyczne powiadamianie użytkowników o nowych mandatów. Aplikacja została stworzona w oparciu o nowoczesne technologie, co gwarantuje jej niezawodność.

## Funkcjonalności

### 1. Dodawanie Mandatu
- **Wybór typu pracownika**:
    - Pracownik fizyczny: Wybór z listy rozwijanej zawierającej aktywnych i zwolnionych pracowników ze wszystkich spółek.
    - Pracownik biurowy: Ręczne wprowadzenie imienia i nazwiska.
- **Wymagane pola**:
    - **Sygnatura**: Unikalna identyfikacja mandatu; duplikaty są automatycznie blokowane.
    - **Data wykroczenia**: Określenie daty popełnienia wykroczenia.
    - **Powód wykroczenia**: Wybór z gotowych opcji (np. przekroczenie prędkości, zakaz parkowania) lub wprowadzenie własnego powodu.
    - **Kwota i waluta**: Podanie kwoty oraz wybór waluty (PLN lub EUR).
    - **Opłata administracyjna**: Domyślna wartość 100 PLN jest dodawana automatycznie.
    - **Termin płatności**: Ustawienie daty, również wstecznej.
    - **Załącznik**: Możliwość dołączenia dokumentu w formacie PDF.
- **Opcje formularza**:
    - **Anuluj**: Odrzucenie zmian.
    - **Zapisz**: Zapisanie mandatu w systemie.
    - **Zamknij**: Wyjście z formularza bez zapisywania.

### 2. Powiadomienia
- Automatyczne wysyłanie wiadomości e-mail do wskazanego odbiorcy (`j.kowalski@test.pl`) po dodaniu nowego mandatu.
    - **Temat**: "Mandat nr. [sygnatura] - Prośba o opłacenie".
    - **Treść**: Szczegóły mandatu, termin płatności oraz link do szczegółowych informacji.

### 3. Zarządzanie Mandatami
- **Potwierdzenie płatności**: Zmiana statusu mandatu na "opłacony".
- **Ograniczenia**:
    - Usuwanie i edycja są niedostępne dla opłaconych mandatów.
    - Załączniki można przeglądać i usuwać, ale nie edytować.

---

## Widoki i Filtry

### 1. Tabela Mandatów
Wyświetla szczegóły wszystkich mandatów, takie jak:
- Imię i nazwisko oraz spółka pracownika.
- Dane kontaktowe, sygnatura mandatu i data wykroczenia.
- Powód, kwota, opłata administracyjna i status płatności (kolory: zielony - "opłacony", czerwony - "do zapłaty").

### 2. Opcje Filtrowania
Filtrowanie mandatów według:
- **Statusu**: Opłacony, Do zapłaty.
- **Waluty**: PLN, EUR.
- **Powodu**: Przekroczenie prędkości, zakaz parkowania itp.
- **Dat**: Data wykroczenia lub termin płatności.
- **Spółki**: EcoPro, NetX, TechLab, pracownik biurowy.

### 3. Wyszukiwanie
Wyszukiwanie mandatów według:
- Imienia i nazwiska pracownika.
- Sygnatury.
- Numeru telefonu.

### 4. Sortowanie
Sortowanie tabeli według dowolnej kolumny w porządku rosnącym lub malejącym.

---

## Stos technologiczny
- **Backend**: Spring Boot 3 + Maven.
- **Frontend**: Thymeleaf.
- **Baza danych**: Microsoft SQL Server + Flyway.
- **Kontrola wersji**: GitHub.

---

## Instrukcja instalacji

### 1. Sklonuj repozytorium z GitHuba.
### 2. Skonfiguruj połączenie z bazą danych oraz ustawienia serwera SMTP w pliku `application.properties`:

```properties
spring.application.name=ticket
server.port=8087

spring.datasource.url=jdbc:mysql://localhost:3306/ticket_db?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=admin
spring.datasource.password=admin
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true

email.sender=no-reply@ticket.eu
spring.mail.host=127.0.0.1
spring.mail.port=8025
spring.mail.username=myuser
spring.mail.password=mysecretpassword
spring.mail.properties.mail.smtp.auth=false
spring.mail.properties.mail.smtp.starttls.enable=false

ticket.default-administrative-fee=100
```

### 3. Konfiguracja serwera SMTP
Do celów testowych można uruchomić lokalny serwer SMTP, np. **Fake SMTP**. Aby to zrobić, pobierz plik jar z [Fake SMTP Server - wersja 2.0.0](https://github.com/gessnerfl/fake-smtp-server/releases/tag/2.0.0) i uruchom go lokalnie.


### 4. Uruchom baze danych z Docker Compose.
Aby uruchomić kontener z bazą danych, użyj pliku docker-compose.yml, który znajduje się w repozytorium. Uruchom następujące polecenie w katalogu, w którym znajduje się plik docker-compose.yml:
```
docker-compose up --build
```

### 5. Zbuduj projekt za pomocą Mavena: mvn clean install.

### 6. Uruchom aplikację w konsoli 
```
java -jar target/nazwa-aplikacji-<wersja>.jar
```