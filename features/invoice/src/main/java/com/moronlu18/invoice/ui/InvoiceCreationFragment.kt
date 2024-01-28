package com.moronlu18.invoice.ui

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.moronlu18.InvoiceDavid.entity.LineaItem
import com.moronlu18.invoice.adapter.AdaptadorArticulos
import com.moronlu18.invoice.entity.Invoice
import com.moronlu18.invoice.usecase.InvoiceViewModel
import com.moronlu18.invoiceFragment.databinding.FragmentInvoiceCreationBinding
import com.moronlu18.item.entity.item
import com.moronlu18.task.calendar.CalendarInvoice
import java.time.Instant


//data class Articulo(val nombre:String,val precio:Double)
class InvoiceCreationFragment : Fragment() {
    lateinit var factura: Invoice
    private var editar = false

    private var calendar = CalendarInvoice()
    private var _binding: FragmentInvoiceCreationBinding? = null
    private val binding
        get() = _binding!!


    private val viewModel: InvoiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    inner class textWatcher(var t: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            // println("illoooooo ${t.toString()}")
            viewModel.introduceCliente()
            t.isErrorEnabled = false
            //binding.Email.requestFocus()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvoiceCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // val itemListString = articulos.map { articulo -> articulo.nombre }
        val adaptersp =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, viewModel.RawArticulos)

        adaptersp.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)


        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                InvoiceState.idClienteEmtyError -> {
                    binding.tilInvoiceCreationIdCliente.error =
                        "Introduce un id de Cliente existente"
                    binding.tilInvoiceCreationIdCliente.requestFocus()
                }

                InvoiceState.idFacturaEmtyError -> {
                    binding.tilInvoiceCreationIdFactura.error = "Introduce un id para la factura"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                InvoiceState.feVenEmtyError -> {
                    binding.tilInvoiceCreationFeVen.error =
                        "Introduce una fecha con formato YYYY/MM/DD"
                    binding.tilInvoiceCreationFeVen.requestFocus()
                }

                InvoiceState.feEmiEmtyError -> {
                    binding.tilInvoiceFeEmi.error = "Introduce una fecha con formato YYYY/MM/DD"
                    binding.tilInvoiceFeEmi.requestFocus()
                }

                InvoiceState.facturaValidateError -> {
                    binding.tilInvoiceCreationIdFactura.error =
                        "Id de la factura invalido, debe ser un id no existente"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                InvoiceState.facturaNewIdError -> {
                    binding.tilInvoiceCreationIdFactura.error =
                        "Id de la factura invalido, para editar el id debe existir"
                    binding.tilInvoiceCreationIdFactura.requestFocus()
                }

                InvoiceState.idClienteInvalidError -> {
                    binding.tilInvoiceCreationIdCliente.error =
                        "Introduce un id de cliente existente"
                    binding.tilInvoiceCreationIdCliente.requestFocus()
                }

                InvoiceState.feVenInvalidError -> {
                    binding.tilInvoiceCreationFeVen.error =
                        "Formato de la fecha no válido, YYYY-MM-DD"
                    binding.tilInvoiceCreationFeVen.requestFocus()
                }

                InvoiceState.feEmiInvalidError -> {
                    binding.tilInvoiceFeEmi.error = "Formato de la fecha no válido, YYYY-MM-DD"
                    binding.tilInvoiceFeEmi.requestFocus()
                }

                InvoiceState.dateInvalidError -> {
                    println("Erroe en la fecha")
                    Toast.makeText(
                        requireContext(),
                        "La fecha de vencimiento debe ser mayor que la fecha de Emisión",
                        Toast.LENGTH_LONG
                    ).show()
                }

                InvoiceState.ArticulosEmptyError -> Toast.makeText(
                    requireContext(), "Introduce algún artículo", Toast.LENGTH_SHORT
                ).show()

                else -> Created()
            }


        }

        binding.tieInvoiceFeEmi.setOnClickListener {
            calendar.showDatePickerDialog(parentFragmentManager) { day, month, year ->
                binding.tieInvoiceFeEmi.setText(
                    String.format(
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        day
                    )
                )
            }
        }
        binding.tieInvoiceCreationFeVen.setOnClickListener {
            calendar.showDatePickerDialog(parentFragmentManager) { day, month, year ->
                binding.tieInvoiceCreationFeVen.setText(
                    String.format(
                        "%04d-%02d-%02d",
                        year,
                        month + 1,
                        day
                    )
                )
            }
        }
        val rvadapter = AdaptadorArticulos(viewModel.articulos, false) { i: Int ->
            viewModel.articulos.removeAt(i)
            //notifyItemRemoved(position)

            binding.rvInvoiceArticulos.adapter?.notifyDataSetChanged()
            updateTotal()

        }
        binding.rvInvoiceArticulos.adapter = rvadapter
        binding.rvInvoiceArticulos.layoutManager = LinearLayoutManager(context)
        binding.spArticulo.adapter = adaptersp


        parentFragmentManager.setFragmentResultListener("key",
            this,
            FragmentResultListener { requestKey, result ->
                editar = true
                val pos: Int = result.getInt("pos")
                factura = viewModel.facturas[pos]
                viewModel.setLista(factura.Articulos)
                viewModel.editar = editar
                val precios = factura.Articulos.map { it.precio }
                binding.rvInvoiceArticulos.adapter =
                    AdaptadorArticulos(factura.Articulos, false) { i: Int ->
                        factura.Articulos.removeAt(i)

                        //notifyItemRemoved(position)
                        binding.rvInvoiceArticulos.adapter?.notifyDataSetChanged()
                        updateTotal()
                    }
                binding.tieInvoiceCreationIdCliente.setText(factura.idCliente.toString())
                viewModel.idFactura.value = factura.id.toString()
                //rellenarCliente(binding.tieInvoiceCreationIdCliente.text)
                viewModel.introduceCliente()

                val posEmi = factura.FeEmision.toString().indexOf('T')
                val posVen = factura.FeVencimiento.toString().indexOf('T')
                binding.tieInvoiceFeEmi.setText(factura.FeEmision.toString().substring(0, posEmi))
                binding.tieInvoiceCreationFeVen.setText(
                    factura.FeVencimiento.toString().substring(0, posVen)
                )

                val SubTotal = precios.reduce { acc, ar -> acc + ar }
                binding.txtInvoiceCreationSubtotal.text = String.format("%.2f €", SubTotal)
                binding.txtInvoiceCreationTotal.text =
                    String.format("%.2f €", SubTotal + (SubTotal * 0.21))

                binding.tieInvoiceCreationIdCliente.addTextChangedListener(
                    textWatcher(
                        binding.tilInvoiceCreationIdCliente
                    )
                )
                binding.tieInvoiceCreationIdFactura.addTextChangedListener(
                    textWatcher(
                        binding.tilInvoiceCreationIdFactura
                    )
                )

            })

        binding.btnArticulos.setOnClickListener {
            if (viewModel.idInvoice() != null) {
                if (editar) {
                    val b: String = binding.spArticulo.selectedItem.toString()
                    val datos = b.split('-')

                    val a = ObtenerItem(datos[0])
                    factura.Articulos.add(LineaItem(a!!.id.value, viewModel.idInvoice()!!,1,a!!.rate,a!!.Iva))
                    rvadapter.notifyDataSetChanged()
                    binding.rvInvoiceArticulos.scrollToPosition(factura.Articulos.size - 1)
                } else {
                    val b: String = binding.spArticulo.selectedItem.toString()
                    val datos = b.split('-')
                    val a = ObtenerItem(datos[0])
                    viewModel.articulos.add(LineaItem(a!!.id.value, viewModel.idInvoice()!!,1,a!!.rate,a!!.Iva))
                    rvadapter.notifyDataSetChanged()
                    binding.rvInvoiceArticulos.scrollToPosition(viewModel.articulos.size - 1)
                }
                updateTotal()

            }else{
                Toast.makeText(requireContext(),"Introduzca un id válido",Toast.LENGTH_LONG).show()
            }

        }

        binding.btnCrear.setOnClickListener {
            if (editar) {
                viewModel.validate()
            } else {
                viewModel.validate()
            }

        }

        binding.tieInvoiceCreationIdCliente.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceCreationIdCliente
            )
        )
        binding.tieInvoiceCreationIdFactura.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceCreationIdFactura
            )
        )
        binding.tieInvoiceFeEmi.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceFeEmi
            )
        )
        binding.tieInvoiceCreationFeVen.addTextChangedListener(
            textWatcher(
                binding.tilInvoiceCreationFeVen
            )
        )


    }

    private fun updateTotal() {
        if (editar) {
            if (factura.Articulos.size < 1) {
                binding.txtInvoiceCreationSubtotal.text = ""
                binding.txtInvoiceCreationTotal.text = ""
            } else {
                val precios = factura.Articulos.map { it.precio }
                val SubTotal = precios.reduce { acc, ar -> acc + ar }
                binding.txtInvoiceCreationSubtotal.text = String.format("%.2f €", SubTotal)
                binding.txtInvoiceCreationTotal.text =
                    String.format("%.2f €", SubTotal + (SubTotal * 0.21))
            }
        } else {
            if (viewModel.articulos.size < 1) {
                binding.txtInvoiceCreationSubtotal.text = ""
                binding.txtInvoiceCreationTotal.text = ""
            } else {
                val precios = viewModel.articulos.map { it.precio }
                val SubTotal = precios.reduce { acc, ar -> acc + ar }
                binding.txtInvoiceCreationSubtotal.text = String.format("%.2f €", SubTotal)
                binding.txtInvoiceCreationTotal.text =
                    String.format("%.2f €", SubTotal + (SubTotal * 0.21))
            }
        }

    }

    private fun SetFecha(fecha: String): Instant {
        val dateString = fecha + "T00:00:00Z"
        val instant = Instant.parse(dateString)
        return instant
    }

    fun Created() {
        findNavController().popBackStack()
    }

    fun ObtenerItem(nombre: String): item? {
        viewModel.RawArticulos.forEach {
            if (nombre.trim().equals(it.name)) {

                return it
            }
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}