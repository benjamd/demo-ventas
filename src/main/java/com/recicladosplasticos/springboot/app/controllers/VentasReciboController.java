package com.recicladosplasticos.springboot.app.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recicladosplasticos.springboot.app.models.dto.VentasReciboBusquedaDTO;
import com.recicladosplasticos.springboot.app.models.dto.VentasReciboNuevoDTO;
import com.recicladosplasticos.springboot.app.models.entity.Banco;
import com.recicladosplasticos.springboot.app.models.entity.Cheque;
import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.DepositoTransferencia;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemCheque;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemDepositoTransferencia;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboFactura;
import com.recicladosplasticos.springboot.app.models.entity.VentasItemReciboRetencion;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;
import com.recicladosplasticos.springboot.app.models.entity.VentasReciboCreditoDebitado;
import com.recicladosplasticos.springboot.app.models.entity.VentasReciboFacturaAcreditada;
import com.recicladosplasticos.springboot.app.models.entity.VentasReciboPendiente;
import com.recicladosplasticos.springboot.app.models.entity.PuntoDeVenta;
import com.recicladosplasticos.springboot.app.models.entity.Retencion;
import com.recicladosplasticos.springboot.app.models.service.BancoService;
import com.recicladosplasticos.springboot.app.models.service.ClienteService;
import com.recicladosplasticos.springboot.app.models.service.VentasFacturaService;
import com.recicladosplasticos.springboot.app.models.service.PuntoDeVentaService;
import com.recicladosplasticos.springboot.app.models.service.VentasReciboService;
import com.recicladosplasticos.springboot.app.models.service.VentasNotaDeCreditoService;
import com.recicladosplasticos.springboot.app.models.service.VentasNotaDeDebitoService;
import com.recicladosplasticos.springboot.app.util.paginator.PageRender;

@Controller
@RequestMapping("/ventas/recibo")
@SessionAttributes({"recibo","recibobusqueda"})
public class VentasReciboController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PuntoDeVentaService puntoDeVentaService;

	@Autowired
	private BancoService bancoService;

	@Autowired
	private VentasFacturaService facturaService;

	@Autowired
	private VentasNotaDeCreditoService creditoService;

	@Autowired
	private VentasNotaDeDebitoService debitoService;

	@Autowired
	private VentasReciboService reciboService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ModelAttribute("clientes")
	public List<Cliente> clientes() {
		List<Cliente> clientes = clienteService.findAll();
		clientes.sort(Comparator.comparing(Cliente::getNombre));
		return clientes;
	}

	@ModelAttribute("puntosdeventa")
	public List<PuntoDeVenta> puntosdeventa() {
		List<PuntoDeVenta> puntosDeVenta = puntoDeVentaService.findByDocumento("Recibo");
		puntosDeVenta.sort(Comparator.comparing(PuntoDeVenta::getNombre));
		return puntosDeVenta;
	}

	@ModelAttribute("bancos")
	public List<Banco> bancos() {
		List<Banco> bancos = bancoService.findAll();
		bancos.sort(Comparator.comparing(Banco::getNombre));
		return bancos;
	}

	@GetMapping("/nuevorecibo")
	public String crearNuevoRecibo(@Valid @ModelAttribute("recibo") VentasReciboNuevoDTO reciboDTO, Model model,
			RedirectAttributes flash) {
		VentasReciboNuevoDTO recibo = new VentasReciboNuevoDTO();
		Cliente cliente = reciboDTO.getCliente();
		List<VentasFactura> ventasFacturas = facturaService.fetchFacturaPendienteByIdWithCliente(cliente.getId());
		if (ventasFacturas != null)
			recibo.setFacturaspendientes(ventasFacturas);
		List<VentasNotaDeDebito> debitos = debitoService.fetchDebitoPendienteByIdWithCliente(cliente.getId());
		if (debitos != null)
			recibo.setDebitospendientes(debitos);
		List<VentasNotaDeCredito> creditos = creditoService.fetchCreditoPendienteByIdWithCliente(cliente.getId());
		if (creditos != null)
			recibo.setCreditospendientes(creditos);
		List<VentasRecibo> recibos = reciboService.fetchReciboPendienteByIdWithCliente(cliente.getId());
		if (recibos != null)
			recibo.setRecibospendientes(recibos);
		recibo.setCliente(cliente);
		model.addAttribute("recibo", recibo);
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Crear Recibo");
		return "/ventas/recibo/nuevorecibo";
	}

	@GetMapping("/nuevo")
	public String crearReciboDTO(@RequestParam(name = "cliente") Long clienteId, Model model,
			RedirectAttributes flash) {
		VentasReciboNuevoDTO recibo = new VentasReciboNuevoDTO();
		Cliente cliente = clienteService.findOne(clienteId);
		List<VentasFactura> ventasFacturas = facturaService.fetchFacturaPendienteByIdWithCliente(cliente.getId());
		if (ventasFacturas != null)
			recibo.setFacturaspendientes(ventasFacturas);
		List<VentasNotaDeDebito> debitos = debitoService.fetchDebitoPendienteByIdWithCliente(cliente.getId());
		if (debitos != null)
			recibo.setDebitospendientes(debitos);
		List<VentasNotaDeCredito> creditos = creditoService.fetchCreditoPendienteByIdWithCliente(cliente.getId());
		if (creditos != null)
			recibo.setCreditospendientes(creditos);
		List<VentasRecibo> recibos = reciboService.fetchReciboPendienteByIdWithCliente(cliente.getId());
		if (recibos != null)
			recibo.setRecibospendientes(recibos);
		recibo.setCliente(cliente);
		model.addAttribute("recibo", recibo);
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Crear Recibo");
		return "/ventas/recibo/nuevo";
	}

	@PostMapping("/nuevo")
	public String guardarRecibo(@Valid @ModelAttribute("recibo") VentasReciboNuevoDTO reciboDTO, BindingResult result,
			Model model,
			@RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fecha,
			@RequestParam(name = "cliente", required = false) Cliente cliente,
			@RequestParam(name = "pVenta") Long puntoDeVentaId,
			@RequestParam(name = "factura_id[]", required = false) Long[] facturaId,
			@RequestParam(name = "credito_id[]", required = false) Long[] creditoId,
			@RequestParam(name = "debito_id[]", required = false) Long[] debitoId,
			@RequestParam(name = "recibo_id[]", required = false) Long[] reciboId,
			@RequestParam(name = "factura_pago[]", required = false) Double[] facturaPago,
			@RequestParam(name = "debito_pago[]", required = false) Double[] debitoPago,
			@RequestParam(name = "orden_pago[]", required = false) Double[] ordenPago, RedirectAttributes flash,
			SessionStatus status) {
		if (facturaId == null && debitoId == null && (creditoId != null || reciboId != null)) {
			flash.addFlashAttribute("error",
					"Error en el Recibo: No puede cagar el recibo solo Notas de Credito o Recibos!");
			log.info("Error en el Recibo: No puede cagar el recibo solo con Notas de Credito!");
			model.addAttribute("cliente", reciboDTO.getCliente());
			model.addAttribute("titulo", "Crear Recibo");
			model.addAttribute("error", "Error: No puede cagar el recibo solo con Notas de Credito o Recibos!");
			return "/ventas/recibo/nuevo";
		}
		PuntoDeVenta ptovta = puntoDeVentaService.findOne(puntoDeVentaId);
		VentasRecibo reciboExistente = null;
		reciboExistente = reciboService.findReciboByPrefijoAndNumero(ptovta.getPrefijo(), ptovta.getNumero());
		if (reciboExistente != null) {
			log.info(
					"Recibo nuevo: Recibo Duplicado! No ha sido cargado, ya existe un Recibo con mismo prefijo y numero");
			flash.addFlashAttribute("error",
					"Recibo Duplicado! No ha sido cargado, ya existe un Recibo con mismo prefijo y numero");
			Long clienteId = reciboDTO.getCliente().getId();
			VentasReciboNuevoDTO recibo = crearReciboNuevoDTO(reciboDTO, clienteId);
			model.addAttribute("clienteId", clienteId);
			model.addAttribute("recibo", recibo);
			model.addAttribute("cliente", reciboDTO.getCliente());
			model.addAttribute("titulo", "Crear Recibo");
			return "redirect:/ventas/recibo/nuevorecibo";
		}
		if (facturaId != null && facturaPago != null) {
			BigDecimal totalAPagar = calcularTotalAPagar(facturaPago);
			BigDecimal totalPago = calcularTotalPago(reciboDTO);
			if (totalPago.compareTo(totalAPagar) < 0) {
				flash.addFlashAttribute("error",
						"Error en el Recibo: El monto total de formas de pago debe ser igual o mayor al importe total a pagar");
				log.info(
						"Error en el Recibo: El monto total de formas de pago debe ser igual o mayor al importe total a pagar");
				model.addAttribute("cliente", reciboDTO.getCliente());
				model.addAttribute("titulo", "Crear Recibo");
				model.addAttribute("error",
						"Error en el Recibo: El monto total de formas de pago debe ser igual o mayor al importe total a pagar");
				return "/ventas/recibo/nuevo";
			}
		}
		if (facturaId != null && reciboId == null && creditoId == null && debitoId == null) {
			BigDecimal totalPago = calcularTotalPago(reciboDTO);
			if (totalPago.compareTo(BigDecimal.valueOf(0)) == 0) {
				flash.addFlashAttribute("error",
						"Error en el Recibo: No puede cargar solamente facturas sin formas de pago");
				log.info("Error en el Recibo: No puede cargar solamente facturas sin formas de pago");
				model.addAttribute("cliente", reciboDTO.getCliente());
				model.addAttribute("titulo", "Crear Recibo");
				model.addAttribute("error",
						"Error en el Recibo: No puede cargar solamente facturas sin formas de pago");
				return "/ventas/recibo/nuevo";
			}
		}
		VentasRecibo recibo = new VentasRecibo();
		Set<VentasFactura> facturasimputadas = new HashSet<VentasFactura>();
		Set<VentasNotaDeCredito> creditosimputados = new HashSet<VentasNotaDeCredito>();
		Set<VentasNotaDeDebito> debitosimputados = new HashSet<VentasNotaDeDebito>();
		Set<VentasRecibo> recibosimputados = new HashSet<VentasRecibo>();
		crearNuevoRecibo(reciboDTO, fecha, facturaId, creditoId, debitoId, reciboId, facturaPago, recibo,
				facturasimputadas, creditosimputados, debitosimputados, recibosimputados);
		PuntoDeVenta puntoDeVenta = clienteService.saveRecibo(recibo, puntoDeVentaId, recibosimputados,
				facturasimputadas, creditosimputados, debitosimputados);
		if (puntoDeVenta == null) {
			log.info("Recibo nueva: No existe el punto de venta para la presupuesto de venta  que desea cargar");
			flash.addFlashAttribute("error", "No existe el punto de venta para la recibo que desea cargar");
			Long clienteId = reciboDTO.getCliente().getId();
			VentasReciboNuevoDTO recibonuevo = crearReciboNuevoDTO(reciboDTO, clienteId);
			model.addAttribute("clienteId", clienteId);
			model.addAttribute("recibo", recibonuevo);
			model.addAttribute("cliente", reciboDTO.getCliente());
			model.addAttribute("titulo", "Crear Recibo");
			return "redirect:/ventas/recibo/nuevorecibo";
		}
		status.setComplete();
		log.info("se creo recibo exitosamente");
		flash.addFlashAttribute("success", "Recibo creado con éxito!");
		return "redirect:/index";
	}

	private void crearNuevoRecibo(VentasReciboNuevoDTO reciboDTO, Date fecha, Long[] facturaId, Long[] creditoId,
			Long[] debitoId, Long[] reciboId, Double[] facturaPago, VentasRecibo recibo,
			Set<VentasFactura> facturasimputadas, Set<VentasNotaDeCredito> creditosimputados,
			Set<VentasNotaDeDebito> debitosimputados, Set<VentasRecibo> recibosimputados) {
		// Levantamos los documentos de la base de datos
		Set<VentasFactura> facturaspendientes = null;
		Set<VentasNotaDeCredito> creditospendientes = null;
		Set<VentasNotaDeDebito> debitospendientes = null;
		Set<VentasRecibo> recibospendientes = null;
		facturaspendientes = buscarFacturasPendientes(facturaId, facturaPago, facturaspendientes);
		creditospendientes = buscarCreditosPendientes(creditoId, creditospendientes);
		debitospendientes = buscarDebitosPendientes(debitoId, debitospendientes);
		recibospendientes = buscarRecibosPendientes(reciboId, recibospendientes);
		// RELACIONAMOS EL RECIBO CON LAS FACTURAS, CREDITOS Y DEBITOS
		vincularNotasDebitoAlRecibo(debitospendientes, recibo);
		vincularNotasCreditoAlRecibo(creditospendientes, recibo);
		// LEVANTAMOS LAS FORMAS DE PAGO
		BigDecimal importeTotalPago = calcularImporteTotalPago(reciboDTO, recibo);
		recibo.setImportetotal(importeTotalPago);
		// Imputamos al recibo nuevo los saldos a cuenta de recibos con saldo pendiente
		BigDecimal saldoAcuentaRecibos = calcularSaldoACuentaRecibosPendientes(recibospendientes, recibo,
				recibosimputados);
		importeTotalPago = importeTotalPago.add(saldoAcuentaRecibos).setScale(2, RoundingMode.HALF_EVEN);
		imputarNotasDebitoANotasCredito(creditospendientes, debitospendientes, recibo, creditosimputados,
				debitosimputados);
		importeTotalPago = imputarImportePagoYNotasCreditoAFacturasPendientes(facturaPago, facturaspendientes,
				creditospendientes, recibo, importeTotalPago, facturasimputadas, creditosimputados);
		importeTotalPago = imputarFormaPagoANotasDebitoPendientes(debitospendientes, recibo, importeTotalPago,
				debitosimputados);
		if (importeTotalPago.compareTo(BigDecimal.valueOf(0)) > 0) {
			recibo.setSaldoacuenta(importeTotalPago);
			recibo.setSaldopendiente(importeTotalPago);
		} else {
			recibo.setSaldoacuenta(BigDecimal.valueOf(0));
			recibo.setSaldopendiente(BigDecimal.valueOf(0));
		}
		if (fecha == null) {
			recibo.setFecha(new Date());
		} else {
			recibo.setFecha(fecha);
		}
		recibo.setCliente(reciboDTO.getCliente());
	}

	private VentasReciboNuevoDTO crearReciboNuevoDTO(VentasReciboNuevoDTO reciboDTO, Long clienteId) {
		VentasReciboNuevoDTO recibonuevo = new VentasReciboNuevoDTO();
		recibonuevo.setCliente(reciboDTO.getCliente());
		List<VentasFactura> ventasFacturas = facturaService.fetchFacturaPendienteByIdWithCliente(clienteId);
		if (ventasFacturas != null)
			recibonuevo.setFacturaspendientes(ventasFacturas);
		List<VentasNotaDeDebito> debitos = debitoService.fetchDebitoPendienteByIdWithCliente(clienteId);
		if (debitos != null)
			recibonuevo.setDebitospendientes(debitos);
		List<VentasNotaDeCredito> creditos = creditoService.fetchCreditoPendienteByIdWithCliente(clienteId);
		if (creditos != null)
			recibonuevo.setCreditospendientes(creditos);
		List<VentasRecibo> recibos = reciboService.fetchReciboPendienteByIdWithCliente(clienteId);
		if (recibos != null)
			recibonuevo.setRecibospendientes(recibos);
		return recibonuevo;
	}

	private void vincularNotasCreditoAlRecibo(Set<VentasNotaDeCredito> creditospendientes, VentasRecibo recibo) {
		if (creditospendientes != null) {
			for (VentasNotaDeCredito credito : creditospendientes) {
				VentasItemReciboCredito itemReciboCredito = new VentasItemReciboCredito();
				itemReciboCredito.setCredito(credito);
				itemReciboCredito.setImportepago(credito.getSaldopendiente());
				recibo.addItemReciboCredito(itemReciboCredito);
			}
		}
	}

	private void vincularNotasDebitoAlRecibo(Set<VentasNotaDeDebito> debitospendientes, VentasRecibo recibo) {
		if (debitospendientes != null) {
			for (VentasNotaDeDebito debito : debitospendientes) {
				VentasItemReciboDebito itemReciboDebito = new VentasItemReciboDebito();
				itemReciboDebito.setDebito(debito);
				itemReciboDebito.setImportepago(debito.getSaldopendiente());
				recibo.addItemReciboDebito(itemReciboDebito);
			}
		}
	}

	private BigDecimal imputarFormaPagoANotasDebitoPendientes(Set<VentasNotaDeDebito> debitospendientes,
			VentasRecibo recibo, BigDecimal importeTotalPago, Set<VentasNotaDeDebito> debitosimputados) {
		if (debitospendientes != null) {
			for (VentasItemReciboDebito itemDebito : recibo.getDebitos()) {
				BigDecimal saldoDebito = itemDebito.getDebito().getSaldopendiente();
				if (saldoDebito.compareTo(BigDecimal.valueOf(0)) == 0
						|| importeTotalPago.compareTo(BigDecimal.valueOf(0)) == 0) {
					itemDebito.setImporteimputado(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
				}
				if (saldoDebito.compareTo(BigDecimal.valueOf(0)) > 0
						&& importeTotalPago.compareTo(BigDecimal.valueOf(0)) > 0
						&& saldoDebito.compareTo(importeTotalPago) >= 0) {
					itemDebito.setImporteimputado(importeTotalPago);
					saldoDebito = saldoDebito.subtract(importeTotalPago).setScale(2, RoundingMode.HALF_EVEN);
					itemDebito.getDebito().setSaldopendiente(saldoDebito);
					debitosimputados.add(itemDebito.getDebito());
					importeTotalPago = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
				}
				if (saldoDebito.compareTo(BigDecimal.valueOf(0)) > 0
						&& importeTotalPago.compareTo(BigDecimal.valueOf(0)) > 0
						&& saldoDebito.compareTo(importeTotalPago) < 0) {
					itemDebito.setImporteimputado(saldoDebito);
					importeTotalPago = importeTotalPago.subtract(saldoDebito).setScale(2, RoundingMode.HALF_EVEN);
					itemDebito.getDebito().setSaldopendiente(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
					debitosimputados.add(itemDebito.getDebito());
				}
			}

		}
		return importeTotalPago;
	}

	private BigDecimal imputarImportePagoYNotasCreditoAFacturasPendientes(Double[] facturaPago,
			Set<VentasFactura> facturaspendientes, Set<VentasNotaDeCredito> creditospendientes, VentasRecibo recibo,
			BigDecimal importeTotalPago, Set<VentasFactura> facturasimputadas,
			Set<VentasNotaDeCredito> creditosimputados) {
		if (facturaspendientes != null) {
			int i = 0;
			for (VentasFactura ventasFactura : facturaspendientes) {
				BigDecimal saldoFactura = ventasFactura.getSaldopendiente();
				saldoFactura = imputarNotasCreditoAFacturasPendientes(creditospendientes, recibo, facturasimputadas,
						creditosimputados, ventasFactura, saldoFactura);
				importeTotalPago = imputarPagoAFacturaPendiente(facturaPago, recibo, importeTotalPago,
						facturasimputadas, i, ventasFactura, saldoFactura);
				i++;
			}
		}
		return importeTotalPago;
	}

	private BigDecimal imputarNotasCreditoAFacturasPendientes(Set<VentasNotaDeCredito> creditospendientes,
			VentasRecibo recibo, Set<VentasFactura> facturasimputadas, Set<VentasNotaDeCredito> creditosimputados,
			VentasFactura ventasFactura, BigDecimal saldoFactura) {
		if (creditospendientes != null) {
			for (VentasNotaDeCredito credito : creditospendientes) {
				BigDecimal saldoCredito = credito.getSaldopendiente();
				if (saldoFactura.compareTo(BigDecimal.valueOf(0)) > 0
						&& saldoCredito.compareTo(BigDecimal.valueOf(0)) > 0) {
					if (saldoFactura.compareTo(saldoCredito) >= 0) {
						VentasReciboFacturaAcreditada facturaAcreditada = new VentasReciboFacturaAcreditada();
						facturaAcreditada.setFactura(ventasFactura);
						facturaAcreditada.setCredito(credito);
						facturaAcreditada.setImportecredito(saldoCredito);
						recibo.addItemReciboFacturaAcreditada(facturaAcreditada);
						saldoFactura = saldoFactura.subtract(saldoCredito);
						credito.setSaldopendiente(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
						ventasFactura.setSaldopendiente(saldoFactura);
						facturasimputadas.add(ventasFactura);
						creditosimputados.add(credito);
					} else {
						VentasReciboFacturaAcreditada facturaAcreditada = new VentasReciboFacturaAcreditada();
						facturaAcreditada.setFactura(ventasFactura);
						facturaAcreditada.setCredito(credito);
						facturaAcreditada.setImportecredito(saldoFactura);
						recibo.addItemReciboFacturaAcreditada(facturaAcreditada);
						credito.setSaldopendiente(saldoCredito.subtract(saldoFactura));
						saldoFactura = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
						ventasFactura.setSaldopendiente(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
						facturasimputadas.add(ventasFactura);
						creditosimputados.add(credito);
					}
				}
			}
		}
		return saldoFactura;
	}

	private BigDecimal imputarPagoAFacturaPendiente(Double[] facturaPago, VentasRecibo recibo,
			BigDecimal importeTotalPago, Set<VentasFactura> facturasimputadas, int i, VentasFactura ventasFactura,
			BigDecimal saldoFactura) {
		VentasItemReciboFactura itemReciboFactura = new VentasItemReciboFactura();
		itemReciboFactura.setFactura(ventasFactura);
		if (i < facturaPago.length) {
			itemReciboFactura.setImportepago(BigDecimal.valueOf(facturaPago[i]).setScale(2, RoundingMode.HALF_EVEN));
		}
		if (saldoFactura.compareTo(BigDecimal.valueOf(0)) == 0
				|| importeTotalPago.compareTo(BigDecimal.valueOf(0)) == 0) {
			itemReciboFactura.setImporteimputado(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
		}
		if (saldoFactura.compareTo(BigDecimal.valueOf(0)) > 0 && importeTotalPago.compareTo(BigDecimal.valueOf(0)) > 0
				&& saldoFactura.compareTo(importeTotalPago) >= 0) {
			itemReciboFactura.setImporteimputado(importeTotalPago);
			saldoFactura = saldoFactura.subtract(importeTotalPago);
			ventasFactura.setSaldopendiente(saldoFactura);
			facturasimputadas.add(ventasFactura);
			importeTotalPago = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		}
		if (saldoFactura.compareTo(BigDecimal.valueOf(0)) > 0 && importeTotalPago.compareTo(BigDecimal.valueOf(0)) > 0
				&& saldoFactura.compareTo(importeTotalPago) < 0) {
			itemReciboFactura.setImporteimputado(saldoFactura);
			importeTotalPago = importeTotalPago.subtract(saldoFactura).setScale(2, RoundingMode.HALF_EVEN);
			ventasFactura.setSaldopendiente(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
			facturasimputadas.add(ventasFactura);
		}
		recibo.addItemReciboFactura(itemReciboFactura);
		return importeTotalPago;
	}

	private void imputarNotasDebitoANotasCredito(Set<VentasNotaDeCredito> creditospendientes,
			Set<VentasNotaDeDebito> debitospendientes, VentasRecibo recibo, Set<VentasNotaDeCredito> creditosimputados,
			Set<VentasNotaDeDebito> debitosimputados) {
		if (creditospendientes != null) {
			for (VentasNotaDeCredito credito : creditospendientes) {
				BigDecimal saldoCredito = credito.getSaldopendiente();
				if (debitospendientes != null) {
					for (VentasNotaDeDebito debito : debitospendientes) {
						BigDecimal saldoDebito = debito.getSaldopendiente();
						if (saldoCredito.compareTo(BigDecimal.valueOf(0)) > 0
								&& saldoDebito.compareTo(BigDecimal.valueOf(0)) > 0) {
							if (saldoCredito.compareTo(saldoDebito) >= 0) {
								VentasReciboCreditoDebitado creditoDebitado = new VentasReciboCreditoDebitado();
								creditoDebitado.setDebito(debito);
								creditoDebitado.setCredito(credito);
								creditoDebitado.setImportedebito(saldoDebito);
								recibo.addItemReciboCreditoDebitado(creditoDebitado);
								saldoCredito = saldoCredito.subtract(saldoDebito).setScale(2, RoundingMode.HALF_EVEN);
								debito.setSaldopendiente(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
								credito.setSaldopendiente(saldoCredito);
								creditosimputados.add(credito);
								debitosimputados.add(debito);
							} else {
								VentasReciboCreditoDebitado creditoDebitado = new VentasReciboCreditoDebitado();
								creditoDebitado.setDebito(debito);
								creditoDebitado.setCredito(credito);
								creditoDebitado.setImportedebito(saldoCredito);
								recibo.addItemReciboCreditoDebitado(creditoDebitado);
								debito.setSaldopendiente(
										saldoDebito.subtract(saldoCredito).setScale(2, RoundingMode.HALF_EVEN));
								saldoCredito = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
								credito.setSaldopendiente(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
								creditosimputados.add(credito);
								debitosimputados.add(debito);
							}
						}
					}
				}
			}
		}
	}

	private BigDecimal calcularSaldoACuentaRecibosPendientes(Set<VentasRecibo> recibospendientes, VentasRecibo recibo,
			Set<VentasRecibo> recibosimputados) {
		BigDecimal saldoAcuentaRecibos = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		if (recibospendientes != null) {
			for (VentasRecibo recibopendiente : recibospendientes) {
				VentasReciboPendiente reciboSaldoPendiente = new VentasReciboPendiente();
				reciboSaldoPendiente.setRecibo(recibopendiente);
				reciboSaldoPendiente.setImportependiente(recibopendiente.getSaldopendiente());
				recibo.addItemReciboReciboPendiente(reciboSaldoPendiente);
				recibosimputados.add(recibopendiente);
				saldoAcuentaRecibos = saldoAcuentaRecibos.add(recibopendiente.getSaldopendiente()).setScale(2,
						RoundingMode.HALF_EVEN);
				recibopendiente.setSaldopendiente(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
			}
		}
		return saldoAcuentaRecibos;
	}

	private Set<VentasRecibo> buscarRecibosPendientes(Long[] reciboId, Set<VentasRecibo> recibospendientes) {
		if (reciboId != null) {
			recibospendientes = new HashSet<VentasRecibo>();
			for (int i = 0; i < reciboId.length; i++) {
				VentasRecibo recibo = reciboService.findOne(reciboId[i]);
				recibospendientes.add(recibo);
			}
		}
		return recibospendientes;
	}

	private Set<VentasNotaDeDebito> buscarDebitosPendientes(Long[] debitoId,
			Set<VentasNotaDeDebito> debitospendientes) {
		if (debitoId != null) {
			debitospendientes = new HashSet<VentasNotaDeDebito>();
			for (int i = 0; i < debitoId.length; i++) {
				VentasNotaDeDebito debito = debitoService.findOne(debitoId[i]);
				debitospendientes.add(debito);
			}
		}
		return debitospendientes;
	}

	private Set<VentasFactura> buscarFacturasPendientes(Long[] facturaId, Double[] facturaPago,
			Set<VentasFactura> facturaspendientes) {
		if (facturaId != null && facturaPago != null) {
			facturaspendientes = new HashSet<VentasFactura>();
			for (int i = 0; i < facturaId.length; i++) {
				VentasFactura ventasFactura = facturaService.findOne(facturaId[i]);
				facturaspendientes.add(ventasFactura);
			}
		}
		return facturaspendientes;
	}

	private Set<VentasNotaDeCredito> buscarCreditosPendientes(Long[] creditoId,
			Set<VentasNotaDeCredito> creditospendientes) {
		if (creditoId != null) {
			creditospendientes = new HashSet<VentasNotaDeCredito>();
			for (int i = 0; i < creditoId.length; i++) {
				VentasNotaDeCredito credito = creditoService.findOne(creditoId[i]);
				creditospendientes.add(credito);
			}
		}
		return creditospendientes;
	}

	private BigDecimal calcularTotalAPagar(Double[] facturaPago) {
		BigDecimal totalAPagar = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		if (facturaPago != null) {
			for (int i = 0; i < facturaPago.length; i++) {
				totalAPagar = totalAPagar.add(BigDecimal.valueOf(facturaPago[i])).setScale(2, RoundingMode.HALF_EVEN);
			}
		}
		return totalAPagar;
	}

	private BigDecimal calcularTotalPago(VentasReciboNuevoDTO reciboDTO) {
		BigDecimal totalPago = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		Double efectivo = reciboDTO.getEfectivo();
		if (efectivo != null)
			totalPago = BigDecimal.valueOf(efectivo).setScale(2, RoundingMode.HALF_EVEN);
		if (reciboDTO.getDepotransfer() != null) {
			for (DepositoTransferencia depositotransferencia : reciboDTO.getDepotransfer()) {
				if (depositotransferencia != null && depositotransferencia.getPago() != null) {
					totalPago = totalPago.add(depositotransferencia.getPago()).setScale(2, RoundingMode.HALF_EVEN);
				}
			}
		}

		if (reciboDTO.getCheques() != null) {
			for (Cheque cheque : reciboDTO.getCheques()) {
				if (cheque != null && cheque.getPagocheque() != null) {
					totalPago = totalPago.add(cheque.getPagocheque()).setScale(2, RoundingMode.HALF_EVEN);
				}
			}
		}

		if (reciboDTO.getRetenciones() != null) {
			for (Retencion retencion : reciboDTO.getRetenciones()) {
				if (retencion != null && retencion.getPago() != null) {
					totalPago = totalPago.add(retencion.getPago()).setScale(2, RoundingMode.HALF_EVEN);
				}
			}
		}
		return totalPago;
	}

	private BigDecimal calcularImporteTotalPago(VentasReciboNuevoDTO reciboDTO, VentasRecibo recibo) {
		BigDecimal importeTotalPago = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
		importeTotalPago = calcularImporteEfectivo(reciboDTO, recibo, importeTotalPago);
		importeTotalPago = calcularImporteDepositoTransferencia(reciboDTO, recibo, importeTotalPago);
		importeTotalPago = calcularImporteCheques(reciboDTO, recibo, importeTotalPago);
		importeTotalPago = calcularImporteRetenciones(reciboDTO, recibo, importeTotalPago);
		return importeTotalPago;
	}

	private BigDecimal calcularImporteRetenciones(VentasReciboNuevoDTO reciboDTO, VentasRecibo recibo,
			BigDecimal importeTotalPago) {
		if (reciboDTO.getRetenciones() != null) {
			for (Retencion retencion : reciboDTO.getRetenciones()) {
				if (retencion != null && retencion.getPago() != null) {
					Retencion retencionNuevo = new Retencion();
					retencionNuevo.setNumero(retencion.getNumero());
					retencionNuevo.setTipo(retencion.getTipo());
					retencionNuevo.setPago(retencion.getPago());
					if (retencion.getFecha() == null) {
						retencionNuevo.setFecha(new Date());
					} else {
						retencionNuevo.setFecha(retencion.getFecha());
					}
					VentasItemReciboRetencion itemretencion = new VentasItemReciboRetencion();
					itemretencion.setRetencion(retencionNuevo);
					recibo.addItemsRetenciones(itemretencion);
					importeTotalPago = importeTotalPago.add(retencion.getPago()).setScale(2, RoundingMode.HALF_EVEN);
				}
			}
		}
		return importeTotalPago;
	}

	private BigDecimal calcularImporteCheques(VentasReciboNuevoDTO reciboDTO, VentasRecibo recibo,
			BigDecimal importeTotalPago) {
		if (reciboDTO.getCheques() != null) {
			for (Cheque cheque : reciboDTO.getCheques()) {
				if (cheque != null && cheque.getPagocheque() != null) {
					Cheque chequeNuevo = new Cheque();
					chequeNuevo.setBanco(cheque.getBanco());
					chequeNuevo.setNumero(cheque.getNumero());
					chequeNuevo.setTipo(cheque.getTipo());
					chequeNuevo.setPagocheque(cheque.getPagocheque());
					if (cheque.getFechaemision() == null) {
						chequeNuevo.setFechaemision(new Date());
					} else {
						chequeNuevo.setFechaemision(cheque.getFechaemision());
					}
					if (cheque.getFechapago() == null) {
						chequeNuevo.setFechapago(new Date());
					} else {
						chequeNuevo.setFechapago(cheque.getFechapago());
					}
					chequeNuevo.setEmisor(cheque.getEmisor());
					chequeNuevo.setCuitemisor(cheque.getCuitemisor());
					chequeNuevo.setPaguesea(cheque.getPaguesea());
					VentasItemCheque itemcheque = new VentasItemCheque();
					itemcheque.setCheque(chequeNuevo);
					recibo.addItemscheques(itemcheque);
					importeTotalPago = importeTotalPago.add(cheque.getPagocheque()).setScale(2, RoundingMode.HALF_EVEN);
				}
			}
		}
		return importeTotalPago;
	}

	private BigDecimal calcularImporteDepositoTransferencia(VentasReciboNuevoDTO reciboDTO, VentasRecibo recibo,
			BigDecimal importeTotalPago) {
		if (reciboDTO.getDepotransfer() != null) {
			for (DepositoTransferencia depositotransferencia : reciboDTO.getDepotransfer()) {
				if (depositotransferencia != null && depositotransferencia.getPago() != null) {
					DepositoTransferencia depotranferNuevo = new DepositoTransferencia();
					depotranferNuevo.setTipo(depositotransferencia.getTipo());
					if (depositotransferencia.getFecha() == null) {
						depotranferNuevo.setFecha(new Date());
					} else {
						depotranferNuevo.setFecha(depositotransferencia.getFecha());
					}
					depotranferNuevo.setPago(depositotransferencia.getPago());
					depotranferNuevo.setComprobante(depositotransferencia.getComprobante());
					depotranferNuevo.setBanco(depositotransferencia.getBanco());
					VentasItemDepositoTransferencia itemdepotranfer = new VentasItemDepositoTransferencia();
					itemdepotranfer.setDepositotranferencia(depotranferNuevo);
					recibo.addItemsdepotransferencias(itemdepotranfer);
					importeTotalPago = importeTotalPago.add(depositotransferencia.getPago()).setScale(2,
							RoundingMode.HALF_EVEN);
				}
			}
		}
		return importeTotalPago;
	}

	private BigDecimal calcularImporteEfectivo(VentasReciboNuevoDTO reciboDTO, VentasRecibo recibo,
			BigDecimal importeTotalPago) {
		Double efectivoDTO = reciboDTO.getEfectivo();
		if (efectivoDTO != null) {
			recibo.setEfectivo(BigDecimal.valueOf(efectivoDTO).setScale(2, RoundingMode.HALF_EVEN));
			importeTotalPago = importeTotalPago.add(BigDecimal.valueOf(efectivoDTO)).setScale(2,
					RoundingMode.HALF_EVEN);
		} else {
			recibo.setEfectivo(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN));
		}
		return importeTotalPago;
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		VentasRecibo recibo = clienteService.findReciboById(id);
		if (recibo == null) {
			flash.addFlashAttribute("error", "El Recibo no existe en la base de datos!");
			return "redirect:/ventas/recibo/listar";
		}
		model.addAttribute("recibo", recibo);
		model.addAttribute("titulo", "RECIBO");
		DecimalFormat df = new DecimalFormat("00000000");
		model.addAttribute("prefijonumero",
				"Numero: " + recibo.getPrefijo().toString() + " - " + df.format(recibo.getNumero()));
		return "/ventas/recibo/ver";
	}

	@GetMapping("/nuevo/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
			RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/ventas/recibo/buscar";
		}
		VentasRecibo recibo = new VentasRecibo();
		recibo.setCliente(cliente);
		model.put("recibo", recibo);
		model.put("titulo", "Crear Recibo");
		return "/ventas/recibo/nuevo";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash,
			Authentication authentication) {
		if (id > 0) {
			VentasRecibo recibo = clienteService.findReciboById(id);
			if (recibo != null) {
				clienteService.deleteRecibo(recibo, id);
				flash.addFlashAttribute("success", "Recibo eliminado con éxito!");
				return "redirect:/ventas/recibo/listar";
			}
		}
		flash.addFlashAttribute("error", "El recibo no existe en la base de datos, no se pudo eliminar!");
		return "redirect:/ventas/recibo/listar";
	}

	@GetMapping(value = "/listar")
	public String listar(@Valid @ModelAttribute("recibobusqueda") VentasReciboBusquedaDTO reciboDTO,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			 HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 50, Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasRecibo> recibos = reciboService.buscarVentasRecibos(reciboDTO, pageRequest);
		PageRender<VentasRecibo> pageRender = new PageRender<VentasRecibo>("/ventas/recibo/listar", recibos);
		model.addAttribute("titulo", "Listado de Recibos");
		model.addAttribute("recibos", recibos);
		model.addAttribute("page", pageRender);
		return "/ventas/recibo/listar";
	}

	@RequestMapping(value = "/buscar")
	public String searchRecibo(Map<String, Object> model) {
		VentasReciboNuevoDTO recibonuevo = new VentasReciboNuevoDTO();
		VentasReciboBusquedaDTO recibobusqueda = new VentasReciboBusquedaDTO();
		model.put("recibonuevo", recibonuevo);
		model.put("recibobusqueda", recibobusqueda);
		model.put("crearrecibo", "Crear Recibo");
		model.put("buscarrecibo", "Buscar Recibo");
		return "/ventas/recibo/buscar";
	}

	@GetMapping(value = "/exportar")
	public String exportarRecibos(@Valid @ModelAttribute("recibobusqueda") VentasReciboBusquedaDTO reciboDTO, BindingResult result,
			@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "prefijo", required = false) String prefijo,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"listado_recibos.xlsx\" ");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		if (result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/recibo/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/recibo/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/recibo/buscar";
		}
		List<VentasRecibo> recibos = reciboService.buscarVentasRecibos(reciboDTO);
		model.addAttribute("fechainicio", reciboDTO.getFechainicio());
		model.addAttribute("fechafin", reciboDTO.getFechafin());
		model.addAttribute("titulo", "Listado de Recibos");
		model.addAttribute("recibos", recibos);
		return "/ventas/recibo/exportar.xlsx";
	}
	
	@PostMapping(value = "/buscar")
	public String resultadoRecibo(@Valid @ModelAttribute("recibobusqueda") VentasReciboBusquedaDTO reciboDTO,
			BindingResult result, @RequestParam(name = "page", defaultValue = "0") int page, Model model,
			@RequestParam(name = "numeromin", required = false) Long numeromin,
			@RequestParam(name = "numeromax", required = false) Long numeromax,
			@RequestParam(name = "importemin", required = false) Double importemin,
			@RequestParam(name = "importemax", required = false) Double importemax,
			@RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechainicio,
			@RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") Date fechafin,
			@RequestParam(name = "codigo", required = false) String codigo,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "prefijo", required = false) String prefijo,
			@RequestParam(name = "razonsocial", required = false) String razonsocial, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request) {
		if (result.hasErrors()) {
			log.info("error redireccionando a busqueda");
			return "redirect:/ventas/recibo/buscar";
		}
		if ((fechainicio != null && fechafin != null) && fechainicio.after(fechafin)) {
			if (!fechainicio.equals(fechafin)) {
				flash.addFlashAttribute("error", "Fecha inicial no puede ser mayor que la final");
				return "redirect:/ventas/recibo/buscar";
			}
		}
		if ((numeromin != null && numeromax != null) && numeromax < numeromin) {
			flash.addFlashAttribute("error", "El rango de numero mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/recibo/buscar";
		}
		if ((importemin != null && importemax != null) && importemax < importemin) {
			flash.addFlashAttribute("error", "El rango de importe mínimo no puede ser mayor que el máximo!");
			return "redirect:/ventas/factura/buscar";
		}
		Pageable pageRequest = PageRequest.of(page, 50, Sort.by("fecha").and(Sort.by("prefijo").and(Sort.by("numero"))));
		Page<VentasRecibo> recibos = reciboService.buscarVentasRecibos(reciboDTO, pageRequest);
		PageRender<VentasRecibo> pageRender = new PageRender<VentasRecibo>("/ventas/recibo/listar", recibos);
		model.addAttribute("titulo", "Listado de Recibos");
		model.addAttribute("recibos", recibos);
		model.addAttribute("page", pageRender);
		return "/ventas/recibo/listar";
	}

}
