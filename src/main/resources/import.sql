 
INSERT INTO users (id,username,nombre, apellido, password,  enabled) VALUES(1, 'admin','admin','admin','$2a$10$aVRL2rEFk5oF14kjuiq2ueTzs97E0qn9rMAPjiPGU4xBwPRN.QxxC', 1);
INSERT INTO users(id,username,nombre, apellido, password,  enabled) VALUES(2,'operador','operador','operador','$2a$10$W/T/NhU3TsFf80ROjSOygORecrJFJaJ16diLWDWQZLUg3sj9Hc4Zi', 1);
INSERT INTO users  (id,username,nombre, apellido, password,  enabled) VALUES(3,'benjamin','benjamin','benjamin','$2a$10$ypxKJ/nQ.cnvygLOH6KYhejOcNkXL0hmhuv00Ka5ubzs0yP9ObgDS', 1);
INSERT INTO users (id,username,nombre, apellido, password,  enabled) VALUES(4,'usuario','usuario','usuario','$2a$10$mm0b056XvLWxHpHTiNZNwuu3hITilR7i32tTfIb4i00UEQRZtjB2y', 1);

INSERT INTO authorities (id, user_id, authority) VALUES(1, 1, 'ROLE_USER');
INSERT INTO authorities (id, user_id, authority) VALUES(2, 1, 'ROLE_ADMIN');
INSERT INTO authorities (id, user_id, authority) VALUES(3, 2, 'ROLE_USER');
INSERT INTO authorities (id, user_id, authority) VALUES(4, 3, 'ROLE_USER');
INSERT INTO authorities (id, user_id, authority) VALUES(5, 3, 'ROLE_ADMIN');
INSERT INTO authorities (id, user_id, authority) VALUES(6, 4, 'ROLE_USER');

 
INSERT INTO tipos_de_documento (id, documento) VALUES(1, 'FC');  
INSERT INTO tipos_de_documento (id, documento) VALUES(2, 'NC');  
INSERT INTO tipos_de_documento (id, documento) VALUES(3, 'ND');  
INSERT INTO tipos_de_documento (id, documento) VALUES(4, 'OE');  
INSERT INTO tipos_de_documento (id, documento) VALUES(5, 'OC');  
INSERT INTO tipos_de_documento (id, documento) VALUES(6, 'OP');  
INSERT INTO tipos_de_documento (id, documento) VALUES(7, 'RC');  
INSERT INTO tipos_de_documento (id, documento) VALUES(8, 'RE');  
INSERT INTO tipos_de_documento (id, documento) VALUES(9, 'DE');  

INSERT INTO paises (id, codigo, nombre, create_at) VALUES(1, 'AR','Argentina', NOW()); 

INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(1, 'CABA','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(2, 'Buenos Aires','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(3, 'La Pampa','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(4, 'Chubut','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(5, 'Neuquén','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(6, 'Santa Cruz','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(7, 'Río Negro','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(8, 'Tierra del Fuego','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(10, 'Mendoza','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(11, 'Córdoba','1', NOW()); 
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(12, 'San Luis','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(13, 'Santa Fe','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(14, 'Entre Ríos','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(15, 'Corrientes','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(16, 'Misiones','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(17, 'San Juan','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(18, 'La Rioja','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(19, 'Tucumán','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(20, 'Catamarca','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(21, 'Salta','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(22, 'Jujuy','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(23, 'Santiago del Estero','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(24, 'Formosa','1', NOW());
INSERT INTO provincias (id, nombre, pais_id, create_at) VALUES(25, 'Chaco','1', NOW());
 
INSERT INTO puntos_de_venta (id, nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES(1, 'Factura A - 0002',  'Factura' , 'A', '0002', 703, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');  

INSERT INTO puntos_de_venta (id, nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES(2, 'Nota de Crédito A - 0002','Nota de Crédito', 'A', '0002', 64, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');

INSERT INTO puntos_de_venta (id, nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES(3, 'Nota de Dédito A - 0002',  'Nota de Débito', 'A', '0002', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');

INSERT INTO puntos_de_venta (id, nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES(4, 'Recibo - 0001',  'Recibo' , 'X', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');  
INSERT INTO puntos_de_venta (id, nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES(5, 'Remito - 0001',  'Remito' , 'R', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');  

INSERT INTO puntos_de_venta (id, nombre, documento, tipo, prefijo, numero, direccion, codigo_postal, localidad, partido, provincia, pais) VALUES(6, 'Devolución - 0001',  'Devolución' , 'D', '0001', 1, 'Calle 31 Y 513', 1903, 'José Hernández', 'La Plata' , 'Buenos Aires','Argentina');  




INSERT INTO ventas_clientes (id, codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(1,1,'Percar', 'Percar SA', 'Industria del Plastico', '20-70462175-3', 'Responsable Inscripto', 'Av. Mitre 1483', '1889', 'Hudson', 'Berazategui', 'Buenos Aires', 'Argentina', 'Juan', 'Percar@percarsa.com', '011 4611-9314', '2017-08-01');
INSERT INTO ventas_clientes (id, codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(2,2, 'VR PLAST', 'VR PLAST', 'Industria del Plastico', '20-70462175-3', 'Responsable Inscripto', 'Lavalle 83', '1778', 'Quilmes', 'Quilmes', 'Buenos Aires', 'Argentina', 'Erika', 'VRPLAST@hotmail.com', '011 4611-9314', '2017-08-01');
INSERT INTO ventas_clientes (id, codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(3,3, 'Todo Esponja', 'Todo Esponja SRL', 'Industria del Plastico', '20-70462175-3', 'Responsable Inscripto', 'Calle 32 nro. 1483', '1900', 'La Plata', 'La Plata', 'Buenos Aires', 'Argentina', 'Daniel', 'Percar@todoesponja.com.ar', '011 4611-9314', '2017-08-01');
INSERT INTO ventas_clientes (id, codigo, nombre, razon_social, actividad, cuit, iva, direccion, codigo_postal, localidad, partido, provincia, pais, contacto, email, telefono, create_at) VALUES(4,4, 'Zaloga', 'Zaloga', 'Industria del Plastico', '20-70462175-3', 'Responsable Inscripto', 'Av. Calchaqui 4713', '1540', 'Hudson', 'Berazategui', 'Buenos Aires', 'Argentina', 'Maria', 'zaloga@gmail.com', '011 4611-5555', '2017-08-01');

 
INSERT INTO unidades (id, nombre, descripcion)  VALUES(1, 'Kgs', 'Kilogramos');
INSERT INTO unidades (id, nombre, descripcion)  VALUES(2, 'Bolsones', 'Bolsones');
 
INSERT INTO productos (id, nombre, unidad_id, precio, stock, descripcion, create_at) VALUES(1, 'PP Negro PLZ', 1, 100, 0,  'Polipropileno negro peletizado', '2017-08-01'); /*1 */
INSERT INTO productos (id, nombre, unidad_id, precio, stock, descripcion, create_at) VALUES(2, 'PEAD Negro PLZ', 1, 100, 0,  'Poliestireno alta densidad Negro peletizado','2017-08-01');
INSERT INTO productos (id, nombre, unidad_id, precio, stock, descripcion, create_at) VALUES(3, 'Alto Peso Molecular Molido', 1, 100, 0,  'Alto Peso Molecular Molido', '2017-08-01');/* 3 */
INSERT INTO productos (id, nombre, unidad_id, precio, stock, descripcion, create_at) VALUES(4, 'PEBD Negro PLZ', 1, 100, 0, 'Poliestireno baja densidad Negro peletizdo','2017-08-01');
INSERT INTO productos (id, nombre, unidad_id, precio, stock, descripcion, create_at) VALUES(5, 'PP Gris PLZ', 2, 75, 0,  'Polipropileno gris peletizado', '2017-08-01'); /* 5 */
INSERT INTO productos (id, nombre, unidad_id, precio, stock, descripcion, create_at) VALUES(6, 'ABS Negro PLZ', 2, 120, 0, 'ABS Negro peletizado', '2017-08-01');
INSERT INTO productos (id, nombre, unidad_id, precio, stock, descripcion, create_at) VALUES(7, 'PEAD Blanco PLZ', 1, 120, 0, 'Poliestireno alta densidad Blanco peletizado', '2017-08-01');/*7 */


INSERT INTO bancos (id, nombre)  VALUES(1,'BANCO DE GALICIA Y BUENOS AIRES S.A.U.');
INSERT INTO bancos (id, nombre)  VALUES(2,'BANCO DE LA NACION ARGENTINA');
INSERT INTO bancos (id, nombre)  VALUES(3,'BANCO DE LA PROVINCIA DE BUENOS AIRES');
INSERT INTO bancos (id, nombre)  VALUES(4,'INDUSTRIAL AND COMMERCIAL BANK OF CHINA');
INSERT INTO bancos (id, nombre)  VALUES(5,'CITIBANK N.A.');
INSERT INTO bancos (id, nombre)  VALUES(6,'BANCO BBVA ARGENTINA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(7,'BANCO DE LA PROVINCIA DE CORDOBA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(8,'BANCO SUPERVIELLE S.A.');
INSERT INTO bancos (id, nombre)  VALUES(9,'BANCO DE LA CIUDAD DE BUENOS AIRES');
INSERT INTO bancos (id, nombre)  VALUES(10,'BANCO PATAGONIA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(11,'BANCO HIPOTECARIO S.A.');
INSERT INTO bancos (id, nombre)  VALUES(12,'BANCO DE SAN JUAN S.A.');
INSERT INTO bancos (id, nombre)  VALUES(13,'BANCO MUNICIPAL DE ROSARIO');
INSERT INTO bancos (id, nombre)  VALUES(14,'BANCO SANTANDER RIO S.A.');
INSERT INTO bancos (id, nombre)  VALUES(15,'BANCO DEL CHUBUT S.A.');
INSERT INTO bancos (id, nombre)  VALUES(16,'BANCO DE SANTA CRUZ S.A.');
INSERT INTO bancos (id, nombre)  VALUES(17,'BANCO DE LA PAMPA');
INSERT INTO bancos (id, nombre)  VALUES(18,'BANCO DE CORRIENTES S.A.');
INSERT INTO bancos (id, nombre)  VALUES(19,'BANCO PROVINCIA DEL NEUQUÉN SOCIEDAD ANONIMA');
INSERT INTO bancos (id, nombre)  VALUES(20,'BRUBANK S.A.U.');
INSERT INTO bancos (id, nombre)  VALUES(21,'BANCO INTERFINANZAS S.A.');
INSERT INTO bancos (id, nombre)  VALUES(22,'HSBC BANK ARGENTINA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(23,'JPMORGAN CHASE BANK, NATIONAL ASSOCIATION');
INSERT INTO bancos (id, nombre)  VALUES(24,'BANCO CREDICOOP COOPERATIVO LIMITADO');
INSERT INTO bancos (id, nombre)  VALUES(25,'BANCO DE VALORES S.A.');
INSERT INTO bancos (id, nombre)  VALUES(26,'BANCO ROELA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(27,'BANCO MARIVA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(28,'BANCO ITAU ARGENTINA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(29,'BANK OF AMERICA, NATIONAL ASSOCIATION');
INSERT INTO bancos (id, nombre)  VALUES(30,'BNP PARIBAS');
INSERT INTO bancos (id, nombre)  VALUES(31,'BANCO PROVINCIA DE TIERRA DEL FUEGO');
INSERT INTO bancos (id, nombre)  VALUES(32,'BANCO DE LA REPUBLICA ORIENTAL DEL URUGUAY');
INSERT INTO bancos (id, nombre)  VALUES(33,'BANCO SAENZ S.A.');
INSERT INTO bancos (id, nombre)  VALUES(34,'BANCO MERIDIAN S.A.');
INSERT INTO bancos (id, nombre)  VALUES(35,'BANCO MACRO S.A.');
INSERT INTO bancos (id, nombre)  VALUES(36,'BANCO COMAFI SOCIEDAD ANONIMA');
INSERT INTO bancos (id, nombre)  VALUES(37,'BANCO DE INVERSION Y COMERCIO EXTERIOR - BICE');
INSERT INTO bancos (id, nombre)  VALUES(38,'BANCO PIANO S.A.');
INSERT INTO bancos (id, nombre)  VALUES(39,'BANCO JULIO SOCIEDAD ANONIMA');
INSERT INTO bancos (id, nombre)  VALUES(40,'BANCO RIOJA SOCIEDAD ANONIMA UNIPERSONAL');
INSERT INTO bancos (id, nombre)  VALUES(41,'BANCO DEL SOL S.A.');
INSERT INTO bancos (id, nombre)  VALUES(42,'NUEVO BANCO DEL CHACO S. A.');
INSERT INTO bancos (id, nombre)  VALUES(43,'BANCO VOII S.A.');
INSERT INTO bancos (id, nombre)  VALUES(44,'BANCO DE FORMOSA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(45,'BANCO CMF S.A.');
INSERT INTO bancos (id, nombre)  VALUES(46,'BANCO DE SANTIAGO DEL ESTERO S.A.');
INSERT INTO bancos (id, nombre)  VALUES(47,'BANCO INDUSTRIAL S.A.');
INSERT INTO bancos (id, nombre)  VALUES(48,'NUEVO BANCO DE SANTA FE SOCIEDAD ANONIMA');
INSERT INTO bancos (id, nombre)  VALUES(49,'BANCO CETELEM ARGENTINA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(50,'BANCO DE SERVICIOS FINANCIEROS S.A.');
INSERT INTO bancos (id, nombre)  VALUES(51,'BANCO BRADESCO ARGENTINA S.A.U.');
INSERT INTO bancos (id, nombre)  VALUES(52,'BANCO DE SERVICIOS Y TRANSACCIONES S.A.');
INSERT INTO bancos (id, nombre)  VALUES(53,'RCI BANQUE S.A.');
INSERT INTO bancos (id, nombre)  VALUES(54,'BACS BANCO DE CREDITO Y SECURITIZACION');
INSERT INTO bancos (id, nombre)  VALUES(55,'BANCO MASVENTAS S.A.');
INSERT INTO bancos (id, nombre)  VALUES(56,'WILOBANK S.A.');
INSERT INTO bancos (id, nombre)  VALUES(57,'NUEVO BANCO DE ENTRE RÍOS S.A.');
INSERT INTO bancos (id, nombre)  VALUES(58,'BANCO COLUMBIA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(59,'BANCO BICA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(60,'BANCO COINAG S.A.');
INSERT INTO bancos (id, nombre)  VALUES(61,'BANCO DE COMERCIO S.A.');
INSERT INTO bancos (id, nombre)  VALUES(62,'BANCO SUCREDITO REGIONAL S.A.U.');
INSERT INTO bancos (id, nombre)  VALUES(63,'BANCO DINO S.A.');
INSERT INTO bancos (id, nombre)  VALUES(64,'BANK OF CHINA LIMITED SUCURSAL BUENOS AIRES');
INSERT INTO bancos (id, nombre)  VALUES(65,'FORD CREDIT COMPAÑIA FINANCIERA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(66,'COMPAÑIA FINANCIERA ARGENTINA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(67,'VOLKSWAGEN FINANCIAL SERVICES COMPAÑIA FINANCIERA');
INSERT INTO bancos (id, nombre)  VALUES(68,'CORDIAL COMPAÑÍA FINANCIERA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(69,'FCA COMPAÑIA FINANCIERA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(70,'GPAT COMPAÑIA FINANCIERA S.A.U.');
INSERT INTO bancos (id, nombre)  VALUES(71,'MERCEDES-BENZ COMPAÑÍA FINANCIERA ARGENTINA');
INSERT INTO bancos (id, nombre)  VALUES(72,'ROMBO COMPAÑÍA FINANCIERA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(73,'JOHN DEERE CREDIT COMPAÑÍA FINANCIERA');
INSERT INTO bancos (id, nombre)  VALUES(74,'PSA FINANCE ARGENTINA COMPAÑÍA FINANCIERA');
INSERT INTO bancos (id, nombre)  VALUES(75,'TOYOTA COMPAÑÍA FINANCIERA DE ARGENTINA');
INSERT INTO bancos (id, nombre)  VALUES(76,'MONTEMAR COMPAÑIA FINANCIERA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(77,'TRANSATLANTICA COMPAÑIA FINANCIERA S.A.');
INSERT INTO bancos (id, nombre)  VALUES(78,'CREDITO REGIONAL COMPAÑIA FINANCIERA S.A');









 
