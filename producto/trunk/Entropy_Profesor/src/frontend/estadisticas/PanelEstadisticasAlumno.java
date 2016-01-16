package frontend.estadisticas;

import frontend.alumnos.DialogPerfilCompleto;
import frontend.auxiliares.CustomTabbedPaneUI;
import frontend.inicio.VentanaPrincipal;
import javax.swing.UIManager;
import backend.Presentacion.Presentacion;
import backend.examenes.Examen;
import backend.gestores.GestorHistorialAlumno;
import backend.reporte.GestorGenerarReporteResolucion;
import backend.resoluciones.Resolucion;
import backend.usuarios.Alumno;
import frontend.auxiliares.LookAndFeelEntropy;
import java.awt.Desktop;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Denise
 */
public class PanelEstadisticasAlumno extends javax.swing.JPanel {

    private final Alumno alumno;
    private GestorHistorialAlumno gestorHistorial;
    
    /**
     * Creates new form PanelEstadisticasAlumno
     * @param alumno cuyas estadísticas deben mostrarse.
     */
    public PanelEstadisticasAlumno(Alumno alumno) {
        initComponents();
        UIManager.put("TabbedPane.selected", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.selectedForeground", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.tabAreaBackground", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.background", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.borderHightlightColor", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.contentAreaColor", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.darkShadow", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.focus", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.foreground", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.highlight", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.light", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.selected", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.selectedForeground", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.selectHighlight", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.shadow", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.tabAreaBackground", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.unselectedBackground", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.unselectedTabBackground", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.unselectedTabForeground", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.unselectedTabHighlight", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        UIManager.put("TabbedPane.unselectedTabShadow", LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);


        this.tbpSolapas.setUI(new CustomTabbedPaneUI());
        this.alumno = alumno;
        this.lblAlumno.setText(alumno.getStrApellido()+", "+alumno.getStrNombre());
        this.lblLegajo.setText((alumno.getStrLegajo() != null && !alumno.getStrLegajo().isEmpty()) ? alumno.getStrLegajo() : "---");
        gestorHistorial = new GestorHistorialAlumno();
        cargarExamenesRendidos();
        cargarClasesAsisitidas();
        ocultarColumnas();
    }
    
    private void cargarExamenesRendidos()
    {
        ArrayList<Resolucion> resolucion = gestorHistorial.getResoluciones(alumno.getIntAlumnoId());
        DefaultTableModel modeloTabla = (DefaultTableModel)jtExamenesRendidos.getModel();
        for (int i = 0; i < resolucion.size(); i++) {
            if(resolucion.get(i).getExamen()!=null)
            {
            modeloTabla.addRow(new Vector());
            Examen examen = gestorHistorial.getExamene(resolucion.get(i).getExamen().getIntExamenId());
            resolucion.get(i).setExamen(examen);
            modeloTabla.setValueAt(examen.getStrNombre(), i, 0);
            modeloTabla.setValueAt(resolucion.get(i).getCalificacion(), i, 1);
            String fechaString = new SimpleDateFormat("yyyy-MM-dd").format(examen.getDteFecha()); 
            modeloTabla.setValueAt(fechaString, i, 2);
            modeloTabla.setValueAt(resolucion.get(i), i, 3);
            }
        }
        ocultarColumnas();
        jtExamenesRendidos.setModel(modeloTabla);  
    }
    
    private void cargarClasesAsisitidas()
    {
        ArrayList<Presentacion>  presentaciones = gestorHistorial.getAsisntencias(alumno.getIntAlumnoId());
        DefaultTableModel modeloTabla = (DefaultTableModel)jtClasesAsistidas.getModel();
        for (int i = 0; i < presentaciones.size(); i++) {
            modeloTabla.addRow(new Vector());
            modeloTabla.setValueAt(presentaciones.get(i).getStrNombre(), i, 0);
            modeloTabla.setValueAt(presentaciones.get(i).getStrDescripcion(), i, 1);
            modeloTabla.setValueAt(presentaciones.get(i).getDteFecha(), i, 2);
            modeloTabla.setValueAt(presentaciones.get(i), i, 3);
        }
        jtClasesAsistidas.setModel(modeloTabla);
    }
    
    private void ocultarColumnas()
    {
        jtClasesAsistidas.getColumnModel().getColumn(3).setMaxWidth(0);
        jtClasesAsistidas.getColumnModel().getColumn(3).setMinWidth(0);
        jtClasesAsistidas.getColumnModel().getColumn(3).setPreferredWidth(0);
        jtExamenesRendidos.getColumnModel().getColumn(3).setMaxWidth(0);
        jtExamenesRendidos.getColumnModel().getColumn(3).setMinWidth(0);
        jtExamenesRendidos.getColumnModel().getColumn(3).setPreferredWidth(0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDatosExamen = new javax.swing.JPanel();
        upperSeparator = new javax.swing.JSeparator();
        lowerSeparator = new javax.swing.JSeparator();
        lblsAlumno = new javax.swing.JLabel();
        lblAlumno = new javax.swing.JLabel();
        lblsLegajo = new javax.swing.JLabel();
        lblLegajo = new javax.swing.JLabel();
        lblPerfilCompleto = new javax.swing.JLabel();
        tbpSolapas = new javax.swing.JTabbedPane();
        pnlGeneral = new javax.swing.JPanel();
        lblsRendidos = new javax.swing.JLabel();
        lblRendidos = new javax.swing.JLabel();
        lblsAprobados = new javax.swing.JLabel();
        lblAprobados = new javax.swing.JLabel();
        lblsCorregidos = new javax.swing.JLabel();
        lblCorregidos = new javax.swing.JLabel();
        lblsPorcentajeAprobados = new javax.swing.JLabel();
        lblPorcentajeAprobados = new javax.swing.JLabel();
        lblsNotaPromedio = new javax.swing.JLabel();
        lblNotaPromedio = new javax.swing.JLabel();
        lblsNotaMayor = new javax.swing.JLabel();
        lblNotaMayor = new javax.swing.JLabel();
        lblsNotaMenor = new javax.swing.JLabel();
        lblNotaMenor = new javax.swing.JLabel();
        pnlExamenes = new javax.swing.JPanel();
        jspExamenesRendidos = new javax.swing.JScrollPane();
        jtExamenesRendidos = new javax.swing.JTable();
        pnlAsistencias = new javax.swing.JPanel();
        jspClasesAsistidas = new javax.swing.JScrollPane();
        jtClasesAsistidas = new javax.swing.JTable();

        pnlDatosExamen.setBackground(LookAndFeelEntropy.COLOR_TABLA_PRIMARIO);
        pnlDatosExamen.setMaximumSize(new java.awt.Dimension(32767, 109));
        pnlDatosExamen.setName(""); // NOI18N

        upperSeparator.setForeground(LookAndFeelEntropy.COLOR_ENTROPY);

        lowerSeparator.setForeground(LookAndFeelEntropy.COLOR_ENTROPY);

        lblsAlumno.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblsAlumno.setForeground(LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        lblsAlumno.setText("Alumno:");

        lblAlumno.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblAlumno.setText("Alumno");

        lblsLegajo.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblsLegajo.setForeground(LookAndFeelEntropy.COLOR_FUENTE_TITULO_PANEL);
        lblsLegajo.setText("Legajo:");

        lblLegajo.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblLegajo.setText("---");

        lblPerfilCompleto.setFont(LookAndFeelEntropy.FUENTE_TITULO);
        lblPerfilCompleto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frontend/imagenes/ic_estadisticas_alumno_25x25.png"))); // NOI18N
        lblPerfilCompleto.setText("Ver perfil completo");
        lblPerfilCompleto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPerfilCompleto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPerfilCompletoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlDatosExamenLayout = new javax.swing.GroupLayout(pnlDatosExamen);
        pnlDatosExamen.setLayout(pnlDatosExamenLayout);
        pnlDatosExamenLayout.setHorizontalGroup(
            pnlDatosExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosExamenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lowerSeparator, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(upperSeparator)
                    .addGroup(pnlDatosExamenLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlDatosExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPerfilCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlDatosExamenLayout.createSequentialGroup()
                                .addGroup(pnlDatosExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblsLegajo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblsAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlDatosExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblLegajo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        pnlDatosExamenLayout.setVerticalGroup(
            pnlDatosExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosExamenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(upperSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsAlumno)
                    .addComponent(lblAlumno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosExamenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsLegajo)
                    .addComponent(lblLegajo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPerfilCompleto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowerSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        lblsRendidos.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblsRendidos.setText("Exámenes Rendidos:");

        lblRendidos.setFont(LookAndFeelEntropy.FUENTE_NEGRITA);
        lblRendidos.setText("30");

        lblsAprobados.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblsAprobados.setText("Aprobados / Corregidos:");

        lblAprobados.setFont(LookAndFeelEntropy.FUENTE_NEGRITA);
        lblAprobados.setText("15/30");

        lblsCorregidos.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblsCorregidos.setText("Exámenes Corregidos:");

        lblCorregidos.setFont(LookAndFeelEntropy.FUENTE_NEGRITA);
        lblCorregidos.setText("30");

        lblsPorcentajeAprobados.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblsPorcentajeAprobados.setText("Porcentaje de aprobados:");

        lblPorcentajeAprobados.setFont(LookAndFeelEntropy.FUENTE_NEGRITA);
        lblPorcentajeAprobados.setText("50%");

        lblsNotaPromedio.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblsNotaPromedio.setText("Nota promedio:");

        lblNotaPromedio.setFont(LookAndFeelEntropy.FUENTE_NEGRITA);
        lblNotaPromedio.setText("5");

        lblsNotaMayor.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblsNotaMayor.setText("Mayor nota obtenida:");

        lblNotaMayor.setFont(LookAndFeelEntropy.FUENTE_NEGRITA);
        lblNotaMayor.setText("7");

        lblsNotaMenor.setFont(LookAndFeelEntropy.FUENTE_REGULAR);
        lblsNotaMenor.setText("Menor nota obtenida:");

        lblNotaMenor.setFont(LookAndFeelEntropy.FUENTE_NEGRITA);
        lblNotaMenor.setText("2");

        javax.swing.GroupLayout pnlGeneralLayout = new javax.swing.GroupLayout(pnlGeneral);
        pnlGeneral.setLayout(pnlGeneralLayout);
        pnlGeneralLayout.setHorizontalGroup(
            pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblsNotaMenor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblsNotaMayor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblsNotaPromedio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblsPorcentajeAprobados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblsRendidos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblsCorregidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblsAprobados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRendidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAprobados, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                    .addComponent(lblCorregidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPorcentajeAprobados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNotaPromedio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNotaMayor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNotaMenor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGeneralLayout.setVerticalGroup(
            pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsRendidos)
                    .addComponent(lblRendidos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsCorregidos)
                    .addComponent(lblCorregidos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsAprobados)
                    .addComponent(lblAprobados))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsPorcentajeAprobados)
                    .addComponent(lblPorcentajeAprobados))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsNotaPromedio)
                    .addComponent(lblNotaPromedio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsNotaMayor)
                    .addComponent(lblNotaMayor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsNotaMenor)
                    .addComponent(lblNotaMenor))
                .addContainerGap(164, Short.MAX_VALUE))
        );

        tbpSolapas.addTab("General", pnlGeneral);

        jspExamenesRendidos.setBorder(null);

        jtExamenesRendidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Examen", "Nota", "Fecha", "Objeto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtExamenesRendidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtExamenesRendidosMouseClicked(evt);
            }
        });
        jspExamenesRendidos.setViewportView(jtExamenesRendidos);

        javax.swing.GroupLayout pnlExamenesLayout = new javax.swing.GroupLayout(pnlExamenes);
        pnlExamenes.setLayout(pnlExamenesLayout);
        pnlExamenesLayout.setHorizontalGroup(
            pnlExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
            .addGroup(pnlExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlExamenesLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jspExamenesRendidos, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlExamenesLayout.setVerticalGroup(
            pnlExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
            .addGroup(pnlExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlExamenesLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jspExamenesRendidos, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)))
        );

        tbpSolapas.addTab("Exámenes", pnlExamenes);

        jspClasesAsistidas.setBorder(null);

        jtClasesAsistidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Descripción", "Fecha", "Objeto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jspClasesAsistidas.setViewportView(jtClasesAsistidas);

        javax.swing.GroupLayout pnlAsistenciasLayout = new javax.swing.GroupLayout(pnlAsistencias);
        pnlAsistencias.setLayout(pnlAsistenciasLayout);
        pnlAsistenciasLayout.setHorizontalGroup(
            pnlAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
            .addGroup(pnlAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlAsistenciasLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jspClasesAsistidas, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlAsistenciasLayout.setVerticalGroup(
            pnlAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
            .addGroup(pnlAsistenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlAsistenciasLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jspClasesAsistidas, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        tbpSolapas.addTab("Asistencias", pnlAsistencias);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tbpSolapas)
                    .addComponent(pnlDatosExamen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDatosExamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbpSolapas)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblPerfilCompletoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPerfilCompletoMouseClicked
        new DialogPerfilCompleto(VentanaPrincipal.getInstancia(), true, alumno).setVisible(true);
    }//GEN-LAST:event_lblPerfilCompletoMouseClicked

    private void jtExamenesRendidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtExamenesRendidosMouseClicked
        if(evt.getClickCount()==2 )
        {
            try {
                Resolucion  resolucion  = (Resolucion) jtExamenesRendidos.getModel().getValueAt(jtExamenesRendidos.getSelectedRow(), 3);
                if(resolucion!=null)
                {
                    GestorGenerarReporteResolucion gestorReporte = new GestorGenerarReporteResolucion(resolucion);
                    gestorReporte.generarReporteResolucion();
                    String pathArchivo= gestorReporte.getResolucion();
                    Path path = Paths.get(pathArchivo);
                    byte[] pdf = Files.readAllBytes(path);
                    File pdfArchivo = new File(pathArchivo);
                    Desktop.getDesktop().open(pdfArchivo);
                }
            }
            catch(Exception e) {
                System.err.println("Ocurrió una excepción creando el PDF:  "+e.toString());
            }
        }
    }//GEN-LAST:event_jtExamenesRendidosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jspClasesAsistidas;
    private javax.swing.JScrollPane jspExamenesRendidos;
    private javax.swing.JTable jtClasesAsistidas;
    private javax.swing.JTable jtExamenesRendidos;
    private javax.swing.JLabel lblAlumno;
    private javax.swing.JLabel lblAprobados;
    private javax.swing.JLabel lblCorregidos;
    private javax.swing.JLabel lblLegajo;
    private javax.swing.JLabel lblNotaMayor;
    private javax.swing.JLabel lblNotaMenor;
    private javax.swing.JLabel lblNotaPromedio;
    private javax.swing.JLabel lblPerfilCompleto;
    private javax.swing.JLabel lblPorcentajeAprobados;
    private javax.swing.JLabel lblRendidos;
    private javax.swing.JLabel lblsAlumno;
    private javax.swing.JLabel lblsAprobados;
    private javax.swing.JLabel lblsCorregidos;
    private javax.swing.JLabel lblsLegajo;
    private javax.swing.JLabel lblsNotaMayor;
    private javax.swing.JLabel lblsNotaMenor;
    private javax.swing.JLabel lblsNotaPromedio;
    private javax.swing.JLabel lblsPorcentajeAprobados;
    private javax.swing.JLabel lblsRendidos;
    private javax.swing.JSeparator lowerSeparator;
    private javax.swing.JPanel pnlAsistencias;
    private javax.swing.JPanel pnlDatosExamen;
    private javax.swing.JPanel pnlExamenes;
    private javax.swing.JPanel pnlGeneral;
    private javax.swing.JTabbedPane tbpSolapas;
    private javax.swing.JSeparator upperSeparator;
    // End of variables declaration//GEN-END:variables
}
