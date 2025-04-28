# Viaje Feliz

Trabajo práctico de **Programación II**: desarrollo de un sistema de gestión de servicios turísticos.

## Descripción

**Viaje Feliz** es una aplicación de gestión de viajes que permite a los clientes:
- Consultar y contratar servicios turísticos simples y paquetes predefinidos.
- Crear paquetes personalizados.
- Pagar, modificar o cancelar servicios.

El sistema implementa conceptos fundamentales de **POO** como **herencia**, **polimorfismo**, **sobreescritura** y **sobrecarga** para modelar la estructura de datos.

## Tecnologías usadas

- **Java**
- **Junit 4**
- **ArrayList**, **HashMap**, **Iterators**
- **StringBuilder** para optimizar concatenación de textos

## Conceptos de POO aplicados

- **Herencia**: reutilización de atributos y métodos comunes a través de jerarquías de clases (Servicio, ServicioSimple, Paquete).
- **Polimorfismo**: cálculo de costo total según el tipo de servicio mediante `calcularCostoTotal()`.
- **Sobreescritura**: redefinición de `calcularCostoTotal()` en clases hijas (Vuelo, Alojamiento, Alquiler, Excursion).
- **Sobrecarga**: varios constructores para agregar diferentes tipos de servicios simples.

## Clases principales

- **ViajeFeliz**: administrador del sistema (clientes, servicios ofrecidos).
- **Cliente**: permite gestionar las contrataciones y pagos de paquetes.
- **Servicio (abstracta)**: base común para todos los servicios.
- **ServicioSimple (abstracta)**: servicios individuales (Vuelo, Alojamiento, Alquiler, Excursion).
- **Paquete (abstracta)**: conjuntos de servicios.
- **PaquetePredefinido**: paquetes fijos para 2 personas.
- **PaquetePersonalizado**: paquetes armados a medida por el cliente.

## Funcionalidades destacadas

- **Agregar/Modificar/Eliminar** servicios y paquetes.
- **Consultar** contrataciones activas y finalizadas.
- **Quitar un servicio** de una contratación en O(n) de complejidad.
- **Validaciones de integridad** (IREP) para garantizar consistencia de datos.

## Análisis de complejidad

- Operaciones principales como buscar cliente y servicio se realizan en **O(1)** utilizando `HashMap`.
- Operaciones de quitar servicios en paquetes tienen una complejidad **O(n)** por el uso de `ArrayList`.

## Invariante de Representación (IREP)

Cada clase contiene su propio IREP que asegura:
- Unicidad de códigos y DNIs.
- Fechas válidas (inicio < fin).
- Valores positivos para costos, tasas y garantías.
- Integridad de listas de servicios.

## Autores

- **Berini Bruno Nicolás**
- **Chaves Ezequiel**

---
