# Sales-Demo / Demo-Ventas

### Index / Indice
1. English
   - Clarification
   - Analysis & Requirements
   - Design
   - Design software decision
   - Changes
2. Español
   - Aclaración
   - Analisis y Requerimientos
   - Diseño
   - Decisiones de diseño de software
   - Cambios
 
### English

### Clarification

The purpose of this repo is showing part of the work made when I had to get requirements from an argentine recycling company which had no administrative software system, and I had to build a solution fitting their bussiness model. 
I will only center in sharing the sales process, analysis, design decisions and source code made while I was building this piece of software.
Beyond sharing experience, the transendental purpose of this repo is cleaning clode and applying best programming practices until all the technical debt erradicated. 

The following analysis, design and decisions are only centered in the sales proccess because the demo is sales only, but maybe one day when I finished refactoring, had enough time and desire I would share the other bussiness processes.

### Analysis & Requirements

A plastic recycling company has de need for a information system for thier daily administrative tasks. There are two owners, one of them is focused in the administrative management, the other one is behind production processes. There is one administrative employee who's in charge of every task and will be the main system user. The company's main activity is centered in turning pastic waste into polypropylene,polyethylene and other derivatives with different properties like colour or density, which are used as raw material in several sectors of the economy, as well as other final products: plastic washers for steel framing houses and spatulas.

In the administration there is one personal computer but no administrative system, instead all client and provider current accounts are managed with spredsheets, one for each company. The client and provider data might be found in the spredsheets, however the main reference is a folder where de data is written down: company and legal name, cuit (argentine company id), address, contacts, telephone and emails.

### Design

### Design software decision

### Changes


### Español

### Aclaración

El propósito de este repositorio es mostrar una parte del trabajo realizado en el cual debí relevar una empresa argentina sin sistema administrativo informático y construir una solución ajustada a su modelo de negocio. 
Solo me centraré en compartir el proceso de ventas, analisis, decisiones de diseño y código fuente hecho mientras construía esta pieza de software. Más allá de compartir la experiencia, el objetivo trascendental de este repositorio es limpiar el código y aplicar las buenas prácticas de programación hasta que toda la deuda técnica sea erradicada. 

El siguiente analisiss, diseño y decisiones sólo se centrará en el proceso de ventas porque la demo es solo ventas, pero tal vez si algún día termino de refactorizar y me encuentro con tiempo y ganas comparta los otros procesos de negocio.

### Análisis y requerimientos

Una empresa recicladora de plásticos tiene la necesidad de adquirir un sistema informático para su administración diaria. Los dueños son dos personas, una se dedica a la gerencia administrativa y la otra sigue los procesos de producción. Actualmente en Administración hay un empleado que realiza las tareas de cada sector administrativo y será el principal usuario del sistema. 
La actividad principal de la empresa se centra convertir residuos plásticos en polipropileno, polietileno u otros derivados con distintas propiedades como color o densidad, que serán vendidos como productos intermedios a clientes de distintos sectores de economía, así también como otros productos finales: arandelas washer y espátulas.

En la administración cuentan con una pc y no disponen de sistema administrativo, toda la gestión de cuentas corrientes de clientes y proveedores se lleva a cabo en planilla excel, una planilla por cada empresa. Los datos de clientes y proveedores pueden encontrarse en la planilla, aunque la referencia principal carpeta donde se escribe a mano todos los datos: nombre fantasía, razón social, cuit, dirección, contacto, teléfonos, emails.

Para la gestión ordenes de pedidos, se confecciona un excel por cada pedido, el cual contiene los datos de la empresa cabecera, nombre y dirección del cliente, listado de hasta 6 items a despachar con el nombre del producto, cantidad kgs. precio unitario/(kg) y total, el precio está expresado en $AR sin IVA. Se suele escribir una observación para el personal de la empresa o el cliente. Este modelo se imprime  por cuadruplicado siendo impreso en una hoja Legal/Oficio. La primer copia queda para el administrador, la segunda copia para el control del dueño de la empresa, el triplicado y cuadruplicado junto con remito oficiales  se entregan al chofer para que confeccione la hojas de ruta, cuando el chofer entrega el pedido deja el remito para el cliente, regresa a la empresa con el triplicado, y remito y pedido cuadruplicado firmado y entrega todo a administración para dar por finalizada la entrega.
Mientras tanto,  la Administración con el original del pedido confecciona la factura en el sistema de online de AFIP, y carga el monto total de la factura en la planilla de cuenta corriente del cliente, donde también se cargan notas de crédito, débito y los pagos realizados. Las planillas de proveedores tienen la misma lógica.
Con la factura Administración completa una planilla mensual de ventas, las columnas del reporte son: fecha, cliente, producto, kgs., precio unitario, precio total, iva débito fiscal, costo unitario, costo total y ganancia bruta(costo total - precio total). El costo unitario se obtiene a partir del proceso de compras.
Hacer la planillas a mano implica que la información sobre el estado mensual esté disponible con un atraso de medio mes en los mejores casos.  

Se solicita que que la primera pantalla del sistema, luego del login exitoso tenga todas las opciones a la vista, además de los menúes.
También piden que el diseño de interfaces web sea lo suficientemente simple para que una persona de aproximadamente 80 años pueda usarlo, no es una exageración, de hecho es la edad aproximada del personal administrativo quien ya está jubilado luego más de 30 años de experiencia en administración de empresas y título universitario. 
El dueño pide que las órdenes de entrega sean renombradas a presupuesto debido a que en algunos casos solo serán presupuestos de venta que no implicarán una venta. 
 
### Diseño

Se procede diseñar un sistema administrativo con maestro productos y clientes, los documentos oficiales: facturas, créditos, débitos, recibos, remitos y devoluciones. Las facturas (créditos y débitos)  para los productos de venta se venden con un impuesto al valor agregado (IVA) del 21%, también existe la posibilidad de realizar estos documentos sin movimiento de ítems con iva 21% o 10.5%, por ejemplo si se vendieran bienes de capital.

La primera entrega del sistema incluye el manejo de productos, clientes, facturas, créditos, débitos y recibos con la posibilidad de consultar la cuenta corriente. Incluyendo los presupuestos y su impresión en xlsx idéntica a como las confeccionaban a mano. 
Se incluye el manejo de puntos de venta, donde se crean nuevas y modifican las secuencias numéricas de documentos.
Los productos, tienen un nombre, descripción y unidad de venta, como kilogramos o bolsones. Se puede crear y editar nuevas y asignar a cualquier producto.

Se acordó que una vez implementado el sistema, se aplicaran cambios y nuevas funcionalidades que el cliente requiera. Una vez terminado el cambio en el código se realizará un deploy de la nueva versión.
Luego del primer deploy, la prioridad de desarrollo se puso en realizar las planillas que el administrador confecciona a mano.   

### Decisiones de Diseño de software

1. El stack tecnológico elegido fue en base a mis conocimientos y experiencia previa con java, spring framework y mysql. De esta manera solo debí actualizar mis conocimientos a las últimas versiones estables para evitar programar una aplicación con un código que envejezca rápidamente.
2. El servidor en el que corre la aplicación es Apache Tomcat 9.  
3. Las tecnologías elegidas para desarrollar la aplicación son java, spring boot web (framework spring) siendo la versión más moderna y productiva: casi nula configuración y se evita editar archivos xml.
4. Para persistir el modelo se usa spring data jpa (implementación por defecto hibernate): delegue el modelado de datos al ORM para centrarme solo realizar CRUD, algunas consultas específicas usando la anotación @Query y solamente instanciando EntityManager para construir las consultas de búsqueda. Esta facilidad se logra con usando la interfaz CrudRepository o JPAPagingAndSorting que hereda de la previamente mencionada. 
5. Base de datos sql: MySQL. siendo la que más conocía, para lo que se necesita hacer es suficiente. La base no va a crecer mucho, realizan pocas operaciones mensuales de venta con montos altos.  
6. La interfaz del usuario web: HTML, CSS, Javascript y Bootstrap, es más flexible de si se tiene que cambiar y lo que más se está usando. 
7. Para generar las vistas se usa Thymeleaf (template engine), mucho más rápido y evitamos una capa de complejidad si delegamos las vistas un frontend como angular, react o vue.js a la que habría que agregar una comunicación api rest al backend.
8. Se implementó spring security para la administración de usuarios: autenticación y autorización. 
9. Se crearon dos roles para el nivel de autorización: role_user y role_admin. Al role_user no se le permite administrar usuarios ni eliminar documentos, solo crear o editar. 
10. La cuenta corriente no es una entidad clase entidad ni está persistida en la base de datos, para acelerar se hizo un reporte que consulta el las facturas, créditos, débitos y recibos para armar el reporte en en formato xlsx.
11. Se pueden imprimir facturas en formato pdf. Su diseño es básico, se postergó un diseño hasta la implementación de factura electrónica.


### Cambios

