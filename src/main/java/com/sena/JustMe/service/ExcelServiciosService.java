package com.sena.JustMe.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sena.JustMe.model.Servicios;
import com.sena.JustMe.model.Usuarios;
import com.sena.JustMe.repository.IUsuarioRepository;

@Service
public class ExcelServiciosService {

    @Autowired
    private IServiciosService serviciosService;

    @Autowired
    private IUsuarioRepository usuariosRepository;

    public int leerExcelServicios(MultipartFile file, Integer idUsuarioSesion) throws Exception {

        int totalGuardados = 0;
        Set<String> nombresUnicos = new HashSet<>();

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                // EXTRAER VALORES (sin columna de imagen en el Excel)
                String nombre = getString(row.getCell(0));
                String descripcion = getString(row.getCell(1));
                String precioStr = getString(row.getCell(2));
                String categoria = getString(row.getCell(3));
                String estado = getString(row.getCell(4));

                // ==========================
                //       VALIDACIONES
                // ==========================

                if (nombre == null || nombre.isEmpty()) {
                    System.out.println("Fila " + (i+1) + ": Nombre vacío.");
                    continue;
                }

                if (descripcion == null || descripcion.isEmpty()) {
                    System.out.println("Fila " + (i+1) + ": Descripción vacía.");
                    continue;
                }

                if (precioStr == null || precioStr.trim().isEmpty()) {
                    System.out.println("Fila " + (i+1) + ": Precio vacío.");
                    continue;
                }

                if (!estado.equals("Activo") && !estado.equals("Inactivo")) {
                    System.out.println("Fila " + (i+1) + ": Estado inválido.");
                    continue;
                }

                if (nombresUnicos.contains(nombre)) {
                    System.out.println("Fila " + (i+1) + ": Servicio duplicado.");
                    continue;
                }

                nombresUnicos.add(nombre);

                Usuarios usuario = usuariosRepository.findById(idUsuarioSesion).orElse(null);

                if (usuario == null) {
                    System.out.println("Fila " + (i+1) + ": Usuario de sesión no encontrado.");
                    continue;
                }

                // ==========================
                //       CREAR OBJETO
                // ==========================

                // Normalizar y parsear el precio (acepta comas, símbolos, espacios)
                String precioNormalizado = precioStr
                        .replace(" ", "")
                        .replace("$", "")
                        .replace(",", ".");

                double precio;
                try {
                    precio = Double.parseDouble(precioNormalizado);
                } catch (NumberFormatException ex) {
                    System.out.println("Fila " + (i+1) + ": Precio inválido -> '" + precioStr + "'");
                    continue;
                }

                Servicios servicio = new Servicios();
                servicio.setNombre_servicios(nombre);
                servicio.setDescripcion(descripcion);
                servicio.setImagen("default.jpg");
                servicio.setPrecio_base(precio);
                servicio.setCategoria(categoria);
                servicio.setEstado(estado);
                servicio.setUsuario(usuario);

                serviciosService.guardar(servicio);
                totalGuardados++;

            } catch (Exception e) {
                System.out.println("Error en fila " + (i + 1) + ": " + e.getMessage());
            }
        }

        workbook.close();
        return totalGuardados;
    }

    // =====================================
    //     MÉTODO AUXILIAR DE LECTURA
    // =====================================
    private String getString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue()).trim();

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            default:
                return "";
        }
    }
}
