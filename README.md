# Sales-Demo - Demo-Ventas

### Spanish

### Aclaración

El propósito de este repositorio es mostrar una parte del trabajo realizado en el cual debí relevar una empresa argentina sin sistema administrativo informático y construir una solución ajustada a su modelo de negocio. 
Solo me centraré en compartir el proceso de ventas, el código fuente, el relevamiento y las decisiones de diseño que se realizaron mientras se construía esta pieza de software. Más allá de compartir la experiencia, el objetivo trascendental de este repositorio es limpiar el código y adquirir buenas prácticas de programación hasta eliminar toda la deuda técnica. 

El siguiente relevamiento, analisis, diseño y decisiones sólo se centrará en el proceso de ventas debido a que la demo no otros procesos administrativos de la empresa. Tal vez si algún día termino de refactorizar todo el código y me encuentro con tiempo y ganas comparta otros procesos de negocio.

### Relevamiento

Una empresa recicladora de plásticos tiene la necesidad de adquirir un sistema informático para su administración diaria. Los dueños son dos personas, una se dedica a la gerencia administrativa y la otra sigue los procesos de producción. Actualmente en Administración hay un empleado que realiza las tareas de cada sector administrativo y será el principal usuario del sistema. 
La actividad principal de la empresa se centra convertir residuos plásticos en polipropileno, polietileno u otros derivados con distintas propiedades como color o densidad, que serán vendidos como productos intermedios a clientes de distintos sectores de economía, así también como otros productos finales: arandelas washer y espátulas.
En la administración cuentan con una pc y no disponen de sistema administrativo, toda la gestión de cuentas corrientes de clientes y proveedores se lleva a cabo en planilla excel, una planilla por cada empresa. Los datos de clientes y proveedores pueden encontrarse en la planilla, aunque la referencia principal carpeta donde se escribe a mano todos los datos:
nombre fantasía, razón social, cuit, dirección, contacto, teléfonos, emails.    
Para la gestión ordenes de pedidos, se confecciona un excel por cada pedido, el cual contiene los datos de la empresa cabecera, nombre y dirección del cliente, listado de hasta 6 items a despachar con el nombre del producto, cantidad kgs. precio unitario/(kg) y total, el precio está expresado en $AR sin IVA. Se suele escribir una observación para el personal de la empresa o el cliente. Este modelo se imprime  por cuadruplicado siendo impreso en una hoja Legal/Oficio. La primer copia queda para el administrador, la segunda copia para el control del dueño de la empresa, el triplicado y cuadruplicado junto con remito oficiales  se entregan al chofer para que confeccione la hojas de ruta, cuando el chofer entrega el pedido deja el remito para el cliente, regresa a la empresa con el triplicado, y remito y pedido cuadruplicado firmado y entrega todo a administración para dar por finalizada la entrega.
Mientras tanto,  la Administración con el original del pedido confecciona la factura en el sistema de online de AFIP, y carga el monto total de la factura en la planilla de cuenta corriente del cliente, donde también se cargan notas de crédito, débito y los pagos realizados. Las planillas de proveedores tienen la misma lógica.
Con la factura Administración completa una planilla mensual de ventas, las columnas del reporte son: fecha, cliente, producto, kgs., precio unitario, precio total, iva débito fiscal, costo unitario, costo total y ganancia bruta(costo total - precio total). El costo unitario se obtiene a partir del proceso de compras. 
Se solicita que que la primera pantalla del sistema, luego del login exitoso tenga todas las opciones a la vista, además de los menúes en el navbar. También piden que el diseño de interfaces web sea lo suficientemente simple para que una persona de aproximadamente 80 años pueda usarlo, no es una exageración, de hecho es la edad aproximada del personal administrativo quien ya está jubilado luego más de 30 años de experiencia en administración de empresas y título universitario. 
El dueño pide que las órdenes de entrega sean renombradas a presupuesto debido a que en algunos casos solo serán presupuestos de venta que no implicarán una venta. 
 
### Análisis y Diseño

Se procede diseñar un sistema administrativo con maestro productos y clientes, los documentos oficiales: facturas, créditos, débitos, recibos, remitos y devoluciones. Las facturas (créditos y débitos)  para los productos de venta se venden con un IVA 21%, también existe la posibilidad de realizar estos documentos sin movimiento de ítems con iva 21% o 10.5% si se vendieran bienes de capital. 
La primera entrega del sistema incluye el manejo de productos, clientes, facturas, créditos, débitos y recibos con la posibilidad de consultar la cuenta corriente. Incluyendo los presupuestos y su impresión en xlsx idéntica a como las confeccionaban a mano. 
Se incluye el manejo de puntos de venta, donde se crean nuevas y modifican las secuencias numéricas de documentos.
Los productos, tienen un nombre, descripción y unidad de venta, como kilogramos o bolsones. Se puede crear y editar nuevas y asignar a cualquier producto.
Se acordó que una vez implementado el sistema, se aplicaran cambios y nuevas funcionalidades que el cliente requiera. Una vez terminado el cambio en el código se realizará un deploy de la nueva versión.
Luego del primer deploy, la prioridad de desarrollo se puso en realizar las planillas que el administrador confecciona a mano.   

### Decisiones de Diseño de software

El stack tecnológico elegido fue en base a mis conocimientos y experiencia previa con java, spring framework y mysql. De esta manera solo debí actualizar mis conocimientos a las últimas versiones estables para evitar programar una aplicación con un código que envejezca rápidamente.
El servidor en el que corre la aplicación es Apache Tomcat 9.  
Las tecnologías elegidas para desarrollar la aplicación son java, spring boot web (framework spring) siendo la versión más moderna y productiva: casi nula configuración y se evita editar archivos xml.
Para persistir el modelo se usa spring data jpa (implementación por defecto hibernate): delegue el modelado de datos al ORM para centrarme solo realizar CRUD, algunas consultas específicas usando la anotación @Query y solamente instanciando EntityManager para construir las consultas de búsqueda. Esta facilidad se logra con usando la interfaz CrudRepository o JPAPagingAndSorting que hereda de la previamente mencionada. 
Base de datos sql: MySQL. siendo la que más conocía, para lo que se necesita hacer es suficiente. La base no va a crecer mucho, realizan pocas operaciones mensuales de venta con montos altos.  
La interfaz del usuario web: HTML, CSS, Javascript y Bootstrap, es más flexible de si se tiene que cambiar y lo que más se está usando. 
Para generar las vistas se usa Thymeleaf (template engine), mucho más rápido y evitamos una capa de complejidad si delegamos las vistas un frontend como angular, react o vue.js a la que habría que agregar una comunicación api rest al backend.
Se implementó spring security para la administración de usuarios: autenticación y autorización. 
Se crearon dos roles para el nivel de autorización: role_user y role_admin. Al role_user no se le permite administrar usuarios ni eliminar documentos, solo crear o editar. 
La cuenta corriente no es una entidad clase entidad ni está persistida en la base de datos, para acelerar se hizo un reporte que consulta el las facturas, créditos, débitos y recibos para armar el reporte en en formato xlsx.
Se pueden imprimir facturas en formato pdf. Su diseño es básico, se postergó un diseño hasta la implementación de factura electrónica.


### Cambios

