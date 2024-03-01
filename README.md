

## 01.03.2024 
## 1. Добавлена интеграция
### Зависимости:

```
<dependency>
    <groupId>org.springframework.integration</groupId>
    <artifactId>spring-integration-core</artifactId>
    <version>6.2.2</version>
</dependency>
<dependency>
    <groupId>org.springframework.integration</groupId>
    <artifactId>spring-integration-file</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.integration</groupId>
	<artifactId>spring-integration-feed</artifactId>
</dependency>

```
### Модули:

**config.IntegrationConfig**

**service.FileGateWay**

В контроллер добавлен **FileGateWay**

При внесении изменений в список пользователь в директории **tmp/user_updates** создается файл с id юзера, где записываются старые данные, новые данные и время изменения, например:

Файл 1.txt:
```
User{id=1, firstName='Сергей', lastName='Стяжкин'}
User{id=1, firstName='Сергей', lastName='Иванов'} 2024-03-01T11:54:56.842528400
User{id=1, firstName='Сергей', lastName='Иванов'}
User{id=1, firstName='Сергей', lastName='Петров'} 2024-03-01T11:55:35.187294
```

Файл 2.txt
```
User{id=2, firstName='Макс', lastName='Попов'}
User{id=2, firstName='Макс', lastName='Сидоров'} 2024-03-01T11:55:03.580156900

```

## 2. Добавлен паттерн Observer
При создании нового пользователя в консоли будет отображаться сообщение об этом.

1) Создали событие **UserCreateEvent extends ApplicationEvent**

2) Реализовали слушателя **UserCreateListener implements ApplicationListener<UserCreateEvent>**, который выводит сообщение о создании нового пользователя в консоль

3) Опубликовали событие, изменив метод создания пользователя в **UserService**:
```
    public User saveUser(User user){
        publisher.publishEvent(new UserCreateEvent(this, user));
        return userRepository.save(user);
    }

```

Результат создания пользователя:
```
Добавлен новый пользователь: Сергей Стяжкин
Добавлен новый пользователь: Макс Попов
```


## 28.02.2024 Добавлена настройка мониторинга с Prometheus и Grafana

Зависимости:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-core</artifactId>
    <version>1.12.3</version>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
    <version>1.12.3</version>
</dependency>
```

Файл настройки prometheus.yml:
```
# my global config
global:
  scrape_interval: 15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
    - static_configs:
        - targets:
          # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: "users_prometheus"

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.
    static_configs:
      - targets: ["localhost:9090"]
  - job_name: 'users'
    metrics_path: "/actuator/prometheus"
    static_configs:
      - targets: ["localhost:8080"]

```

![Панель Prometheus](/picture/1.png)

![Метрики Grafana](/picture/2.png)

![Метрики Grafana](/picture/3.png)



