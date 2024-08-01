[English👈](README.en-US.md)

# Customer Management System Rest API

Bu repo basit şekilde bir müşteri yönetim sistemini içerir. Java programlama dili kullanılarak monolithic mimaride layered architecture uygulanmıştır.

## Geliştirme

- [Conventional Commits](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
- `@Data` anotasyonu yerine `@Getter`, `@Setter`, ... gibi anotasyonlar kullanmak bize modülerlik ve esneklik
  sağlamaktadır.
- Package isimleri Oracle dokümanlarına göre lower_snake_case formatında olmalıdır.
- PostgreSQL veritabanı kullanılan servislerde topluluğa göre tablo ve sütun isimleri lower_snake_case formatında
  olmalıdır.
- `var` anahtar kelimesi mümkün olduğu sürece tercih edilmemelidir. Bunun yerine direkt tip belirtilmelidir. Bunun
  sebebi ise `var` anahtar kelimesinin okunabilirliği azaltmasıdır.
- Immutable veriler için record tipleri kullanılmalıdır. Örneğin request ve response objeleri.
- Rest API'lerin dünya genelinde kabul görmüş standartları vardır. Şu linkteki makaleler ile bunlara hakim
  olabilirsiniz: [REST API URI Naming Conventions and Best Practices](https://restfulapi.net/resource-naming/), [Best practices for REST API design](https://stackoverflow.blog/2020/03/02/best-practices-for-rest-api-design/)

## Projeyi Çalıştırma

1. Projeyi İndirin: Proje dosyalarını yerel makinenize klonlayın veya indirin.

2. Docker Uygulamasını Çalıştırın: Docker'ın yüklü ve çalışır durumda olduğundan emin olun.

3. Docker Compose ile Uygulamayı Başlatın: Proje dizininde terminal açın ve 'docker-compose up -d' komutunu çalıştırın.

4. Build işlemi tamamlandıktan sonra gerekli PostgreSQL veritabanı ve uygulamanın kendisi 'customer-management' adlı container'da çalışır hale gelecektir.