

/* Populate tables */
INSERT INTO users (username, password, enabled) VALUES('admin','$2a$10$aVRL2rEFk5oF14kjuiq2ueTzs97E0qn9rMAPjiPGU4xBwPRN.QxxC', 1);
INSERT INTO users (username, password, enabled) VALUES('operador','$2a$10$W/T/NhU3TsFf80ROjSOygORecrJFJaJ16diLWDWQZLUg3sj9Hc4Zi', 1);
INSERT INTO users (username, password, enabled) VALUES('benjamin','$2a$10$ypxKJ/nQ.cnvygLOH6KYhejOcNkXL0hmhuv00Ka5ubzs0yP9ObgDS', 1);
INSERT INTO users (username, password, enabled) VALUES('usuario','$2a$10$mm0b056XvLWxHpHTiNZNwuu3hITilR7i32tTfIb4i00UEQRZtjB2y', 1);

INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(3, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(3, 'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES(4, 'ROLE_USER');



/* Populate tabla puntos_de_venta */ 
INSERT INTO tipos_de_documento (documento) VALUES('FC'); /*1 */
INSERT INTO tipos_de_documento (documento) VALUES('NC'); /*2 */
INSERT INTO tipos_de_documento (documento) VALUES('ND'); /*3 */
INSERT INTO tipos_de_documento (documento) VALUES('OE'); /*4 */
INSERT INTO tipos_de_documento (documento) VALUES('OC'); /*5 */
INSERT INTO tipos_de_documento (documento) VALUES('OP'); /*6 */
INSERT INTO tipos_de_documento (documento) VALUES('RC'); /*7 */
INSERT INTO tipos_de_documento (documento) VALUES('RE'); /*8 */
INSERT INTO tipos_de_documento (documento) VALUES('DE'); /*9 */

INSERT INTO paises (codigo, nombre, create_at) VALUES('AR','Argentina', NOW()); 

INSERT INTO provincias (nombre, pais_id, create_at) VALUES('CABA','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Buenos Aires','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('La Pampa','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Chubut','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Neuquén','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Santa Cruz','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Río Negro','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Tierra del Fuego','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Mendoza','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Córdoba','1', NOW()); 
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('San Luis','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Santa Fe','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Entre Ríos','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Corrientes','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Misiones','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('San Juan','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('La Rioja','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Tucumán','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Catamarca','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Salta','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Jujuy','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Santiago del Estero','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Formosa','1', NOW());
INSERT INTO provincias (nombre, pais_id, create_at) VALUES('Chaco','1', NOW());

/* Populate tabla puntos_de_venta  (nombre, documento, tipo, prefijo, numero)  VALUES( 'nombre', tipo_documento_id , tipo_letra_id , 'prefijo', numero) */
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Factura A - 0002',  'Factura' , 'A', '0002', 703, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina'); /*1 */
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Factura B - 0002', 'Factura', 'B', '0002', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Nota de Crédito A - 0002','Nota de Crédito', 'A', '0002', 64, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Nota de Crédito B - 0002', 'Nota de Crédito', 'B', '0002', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Nota de Dédito A - 0002',  'Nota de Débito', 'A', '0002', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Nota de Dédito B - 0002',  'Nota de Débito', 'B', '0002', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');

INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Recibo - 0001',  'Recibo' , 'X', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina'); /*1 */
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Recibo - 0020',  'Recibo' , 'X', '0020', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina'); /*1 */
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Orden de Entrega - 0001',  'Orden de Entrega' , 'X', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina'); /*1 */
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Remito - 0001',  'Remito' , 'R', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina'); /*1 */
INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Orden de Crédito Ventas - 0001', 'Orden de Crédito Ventas', 'X', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');

INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Orden de Crédito Compras - 0001', 'Orden de Crédito Compras', 'X', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');

INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Orden de Pago - 0001', 'Orden de Pago', 'X', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');

INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Orden de Pago - 0020', 'Orden de Pago', 'X', '0020', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');

INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Devolución - 0001',  'Devolución' , 'D', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina'); /*1 */

INSERT INTO puntos_de_venta (nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES('Orden de Compra - 0001', 'Orden de Compra', 'X', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');



INSERT INTO ventas_clientes (codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(1,'Percar', 'Percar SA', 'Industria del Plastico', '20-70462175-3', 'Responsable Inscripto', 'Av. Mitre 1483', '1889', 'Hudson', 'Berazategui', 'Buenos Aires', 'Argentina', 'Juan', 'Percar@percarsa.com', '011 4611-9314', '2017-08-01');
INSERT INTO ventas_clientes (codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(2, 'VR PLAST', 'VR PLAST', 'Industria del Plastico', '20-70462175-3', 'Responsable Inscripto', 'Lavalle 83', '1778', 'Quilmes', 'Quilmes', 'Buenos Aires', 'Argentina', 'Erika', 'VRPLAST@hotmail.com', '011 4611-9314', '2017-08-01');
INSERT INTO ventas_clientes (codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(3, 'Todo Esponja', 'Todo Esponja SRL', 'Industria del Plastico', '20-70462175-3', 'Responsable Inscripto', 'Calle 32 nro. 1483', '1900', 'La Plata', 'La Plata', 'Buenos Aires', 'Argentina', 'Daniel', 'Percar@todoesponja.com.ar', '011 4611-9314', '2017-08-01');
INSERT INTO ventas_clientes (codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(4, 'Zaloga', 'Zaloga', 'Industria del Plastico', '20-70462175-3', 'Responsable Inscripto', 'Av. Calchaqui 4713', '1540', 'Hudson', 'Berazategui', 'Buenos Aires', 'Argentina', 'Maria', 'zaloga@gmail.com', '011 4611-5555', '2017-08-01');


INSERT INTO compras_proveedores (codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(1,'Ind. Varela', 'Varela SA', ' ', '20-70462175-3', 'Responsable Inscripto', 'Av. Mitre 1483', '1889', 'Hudson', 'Berazategui', 'Buenos Aires', 'Argentina', 'Juan', 'ivarela@indvarela.com', '011 4611-9314', '2017-08-01');
INSERT INTO compras_proveedores (codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(2, 'YPF', 'YPF', 'Proveedor de Combustible', '20-70462175-3', 'Responsable Inscripto', 'Lavalle 83', '1778', 'Quilmes', 'Quilmes', 'Buenos Aires', 'Argentina', 'Erika', 'laplata@ypf.com.ar', '011 4611-9314', '2017-08-01');


/* Populando Tabla de Unidades */
INSERT INTO unidades (nombre, descripcion)  VALUES('Kgs', 'Kilogramos');
INSERT INTO unidades (nombre, descripcion)  VALUES('Bolsones', 'Bolsones');

/* Populate tabla productos */
INSERT INTO productos (nombre, unidad_id, precio, stock, descripcion, create_at) VALUES('PP Negro PLZ', 1, 100, 0,  'Polipropileno negro peletizado', '2017-08-01'); /*1 */
INSERT INTO productos (nombre, unidad_id, precio, stock, descripcion, create_at) VALUES('PEAD Negro PLZ', 1, 100, 0,  'Poliestireno alta densidad Negro peletizado','2017-08-01');
INSERT INTO productos (nombre, unidad_id, precio, stock, descripcion, create_at) VALUES('Alto Peso Molecular Molido', 1, 100, 0,  'Alto Peso Molecular Molido', '2017-08-01');/* 3 */
INSERT INTO productos (nombre, unidad_id, precio, stock, descripcion, create_at) VALUES('PEBD Negro PLZ', 1, 100, 0, 'Poliestireno baja densidad Negro peletizdo','2017-08-01');
INSERT INTO productos (nombre, unidad_id, precio, stock, descripcion, create_at) VALUES('PP Gris PLZ', 2, 75, 0,  'Polipropileno gris peletizado', '2017-08-01'); /* 5 */
INSERT INTO productos (nombre, unidad_id, precio, stock, descripcion, create_at) VALUES('ABS Negro PLZ', 2, 120, 0, 'ABS Negro peletizado', '2017-08-01');
INSERT INTO productos (nombre, unidad_id, precio, stock, descripcion, create_at) VALUES('PEAD Blanco PLZ', 1, 120, 0, 'Poliestireno alta densidad Blanco peletizado', '2017-08-01');/*7 */




/* Populando Tabla de Tipo de Gastos */
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Reparación Maquinarias','Reparación de Maquinarias');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Afila. y comp. de cuchillas','Afilado y compra de cuchillas');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Lubricantes','Lubricantes');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Protección al Personal','Protección al Personal');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Materiales Varios','Materiales Varios');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Gas','Gas');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Pesadas','Pesadas');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Tasa Seguridad e Higiene','Tasa Seguridad e Higiene');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Impuesto Ingresos Brutos','Impuesto Ingresos Brutos');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Teléfonos','Teléfonos');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Combustibles Cta. Cte.','Combustibles Cuenta Corriente');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Alquiler de Maquinas','Alquiler de Maquinas');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Impuestos Varios','Impuestos Varios');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Combus. Autoelevadores','Combustibles Autoelevadores');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Impuesto al Cheque','Impuesto al Cheque');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Insumos Mantenimiento','Insumos Mantenimiento');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Peajes','Peajes');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Honorarios y transf. judic.','Honorarios y transf. judic.');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Luz Eléctrica','Luz Eléctrica');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Fletes','Fletes');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Gastos Camión','Gastos Camión');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Honorarios Abogado','Honorarios Abogado');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Honorarios Contador','Honorarios Contador');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Franqueos y Telegramas','Franqueos y Telegramas');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Seguros','Seguros');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Librería y Papelería','Librería y Papelería');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Mantenimiento Inmueble','Mantenimiento Inmueble');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Intereses Financieros','Intereses Financieros');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Comiciones Bancarias','Comiciones Bancarias');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Gastos Luis','Gastos Luis');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Gastos Damián','Gastos Damián');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Vehiculo Damián','Vehiculo Damián');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Sueldos Empleados','Sueldos Empleados');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Masterbach','Masterbach');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Bolsas materia prima','Bolsas materia prima');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Cajas','Cajas');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Cintas autoadh/streech','Cintas autoadhesivas y film streech');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Retiro de Basura','Retiro de Basura');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Internet','Internet');
INSERT INTO tipo_gastos (nombre, descripcion) VALUES ('Aguinaldos','Aguinaldos');



/* Populando Tabla de Bancos */
INSERT INTO bancos (nombre)  VALUES('BANCO DE GALICIA Y BUENOS AIRES S.A.U.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE LA NACION ARGENTINA');
INSERT INTO bancos (nombre)  VALUES('BANCO DE LA PROVINCIA DE BUENOS AIRES');
INSERT INTO bancos (nombre)  VALUES('INDUSTRIAL AND COMMERCIAL BANK OF CHINA');
INSERT INTO bancos (nombre)  VALUES('CITIBANK N.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO BBVA ARGENTINA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE LA PROVINCIA DE CORDOBA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO SUPERVIELLE S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE LA CIUDAD DE BUENOS AIRES');
INSERT INTO bancos (nombre)  VALUES('BANCO PATAGONIA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO HIPOTECARIO S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE SAN JUAN S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO MUNICIPAL DE ROSARIO');
INSERT INTO bancos (nombre)  VALUES('BANCO SANTANDER RIO S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DEL CHUBUT S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE SANTA CRUZ S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE LA PAMPA');
INSERT INTO bancos (nombre)  VALUES('BANCO DE CORRIENTES S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO PROVINCIA DEL NEUQUÉN SOCIEDAD ANONIMA');
INSERT INTO bancos (nombre)  VALUES('BRUBANK S.A.U.');
INSERT INTO bancos (nombre)  VALUES('BANCO INTERFINANZAS S.A.');
INSERT INTO bancos (nombre)  VALUES('HSBC BANK ARGENTINA S.A.');
INSERT INTO bancos (nombre)  VALUES('JPMORGAN CHASE BANK, NATIONAL ASSOCIATION');
INSERT INTO bancos (nombre)  VALUES('BANCO CREDICOOP COOPERATIVO LIMITADO');
INSERT INTO bancos (nombre)  VALUES('BANCO DE VALORES S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO ROELA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO MARIVA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO ITAU ARGENTINA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANK OF AMERICA, NATIONAL ASSOCIATION');
INSERT INTO bancos (nombre)  VALUES('BNP PARIBAS');
INSERT INTO bancos (nombre)  VALUES('BANCO PROVINCIA DE TIERRA DEL FUEGO');
INSERT INTO bancos (nombre)  VALUES('BANCO DE LA REPUBLICA ORIENTAL DEL URUGUAY');
INSERT INTO bancos (nombre)  VALUES('BANCO SAENZ S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO MERIDIAN S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO MACRO S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO COMAFI SOCIEDAD ANONIMA');
INSERT INTO bancos (nombre)  VALUES('BANCO DE INVERSION Y COMERCIO EXTERIOR - BICE');
INSERT INTO bancos (nombre)  VALUES('BANCO PIANO S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO JULIO SOCIEDAD ANONIMA');
INSERT INTO bancos (nombre)  VALUES('BANCO RIOJA SOCIEDAD ANONIMA UNIPERSONAL');
INSERT INTO bancos (nombre)  VALUES('BANCO DEL SOL S.A.');
INSERT INTO bancos (nombre)  VALUES('NUEVO BANCO DEL CHACO S. A.');
INSERT INTO bancos (nombre)  VALUES('BANCO VOII S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE FORMOSA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO CMF S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE SANTIAGO DEL ESTERO S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO INDUSTRIAL S.A.');
INSERT INTO bancos (nombre)  VALUES('NUEVO BANCO DE SANTA FE SOCIEDAD ANONIMA');
INSERT INTO bancos (nombre)  VALUES('BANCO CETELEM ARGENTINA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE SERVICIOS FINANCIEROS S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO BRADESCO ARGENTINA S.A.U.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE SERVICIOS Y TRANSACCIONES S.A.');
INSERT INTO bancos (nombre)  VALUES('RCI BANQUE S.A.');
INSERT INTO bancos (nombre)  VALUES('BACS BANCO DE CREDITO Y SECURITIZACION');
INSERT INTO bancos (nombre)  VALUES('BANCO MASVENTAS S.A.');
INSERT INTO bancos (nombre)  VALUES('WILOBANK S.A.');
INSERT INTO bancos (nombre)  VALUES('NUEVO BANCO DE ENTRE RÍOS S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO COLUMBIA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO BICA S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO COINAG S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO DE COMERCIO S.A.');
INSERT INTO bancos (nombre)  VALUES('BANCO SUCREDITO REGIONAL S.A.U.');
INSERT INTO bancos (nombre)  VALUES('BANCO DINO S.A.');
INSERT INTO bancos (nombre)  VALUES('BANK OF CHINA LIMITED SUCURSAL BUENOS AIRES');
INSERT INTO bancos (nombre)  VALUES('FORD CREDIT COMPAÑIA FINANCIERA S.A.');
INSERT INTO bancos (nombre)  VALUES('COMPAÑIA FINANCIERA ARGENTINA S.A.');
INSERT INTO bancos (nombre)  VALUES('VOLKSWAGEN FINANCIAL SERVICES COMPAÑIA FINANCIERA');
INSERT INTO bancos (nombre)  VALUES('CORDIAL COMPAÑÍA FINANCIERA S.A.');
INSERT INTO bancos (nombre)  VALUES('FCA COMPAÑIA FINANCIERA S.A.');
INSERT INTO bancos (nombre)  VALUES('GPAT COMPAÑIA FINANCIERA S.A.U.');
INSERT INTO bancos (nombre)  VALUES('MERCEDES-BENZ COMPAÑÍA FINANCIERA ARGENTINA');
INSERT INTO bancos (nombre)  VALUES('ROMBO COMPAÑÍA FINANCIERA S.A.');
INSERT INTO bancos (nombre)  VALUES('JOHN DEERE CREDIT COMPAÑÍA FINANCIERA');
INSERT INTO bancos (nombre)  VALUES('PSA FINANCE ARGENTINA COMPAÑÍA FINANCIERA');
INSERT INTO bancos (nombre)  VALUES('TOYOTA COMPAÑÍA FINANCIERA DE ARGENTINA');
INSERT INTO bancos (nombre)  VALUES('MONTEMAR COMPAÑIA FINANCIERA S.A.');
INSERT INTO bancos (nombre)  VALUES('TRANSATLANTICA COMPAÑIA FINANCIERA S.A.');
INSERT INTO bancos (nombre)  VALUES('CREDITO REGIONAL COMPAÑIA FINANCIERA S.A');
 








 
