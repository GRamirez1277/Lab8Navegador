package NavegadorDeArchivos;

import NavegadorDeArchivos.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;
import java.util.logging.*;
import javax.swing.*;

public class NavegadorDeArchivos extends javax.swing.JInternalFrame  {
    ButtonGroup but = new ButtonGroup();
    JPopupMenu popupMenu = new JPopupMenu();
    public static String nombre,tipo;
    int refresh = 0;
    File file;
    private BasedeAdminArchivos ModeloBASE;
    private OrdenarPorNombre OrdNombre;
    private OrdenarPorFecha OrdFecha;
    private OrdenarPorTipo OrdTipo;
    private OrdenarPorTamaño OrdTamaño;
    String orden = "Default";
    private JMenuItem copyItem,cutItem,createfolder,refreshyep,createfile,pasteItem,organizar,rename;
    
    public NavegadorDeArchivos() {
        System.out.println("Directorio" + System.getProperty("user.dir"));
        
        initComponents();
        jTextArea1.setEditable(false);
        but.add(jRadioButton1);
        but.add(jRadioButton2);
        but.add(jRadioButton4);
        but.add(jRadioButton3);
        but.clearSelection();
        menu();
        File rootDirectory = new File("/Z");
        ModeloBASE = new BasedeAdminArchivos(rootDirectory);
        OrdNombre = new OrdenarPorNombre(rootDirectory);
        OrdFecha = new OrdenarPorFecha(rootDirectory);
        OrdTipo = new OrdenarPorTipo(rootDirectory);
        OrdTamaño = new OrdenarPorTamaño(rootDirectory);

        
        jTree1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        copyItem.setAction(new AbstractAction("Copiar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = (File) jTree1.getLastSelectedPathComponent();
                copyFile(file);
            }
        });

        cutItem.setAction(new AbstractAction("cortar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = (File) jTree1.getLastSelectedPathComponent();
                cutFile(file);
            }
        });

        pasteItem.setAction(new AbstractAction("Pegar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                File LastFile = (File) jTree1.getLastSelectedPathComponent();
                pasteFile(LastFile);
            }
        });
        refreshyep.setAction(new AbstractAction("actualizar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshing();
            }
        });
        createfile.setAction(new AbstractAction("nuevo archivo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = (File) jTree1.getLastSelectedPathComponent();
                try {
                    newFile(file);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "no se ha seleccionado una carpeta", "ERROR", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(NavegadorDeArchivos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        createfolder.setAction(new AbstractAction("nuevo folder") {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = (File) jTree1.getLastSelectedPathComponent();
                try {
                    newFolder(file);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "no se ha seleccionado una carpeta", "ERROR", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(NavegadorDeArchivos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        rename.setAction(new AbstractAction("Renombrar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = (File) jTree1.getLastSelectedPathComponent();
                String renombre = JOptionPane.showInputDialog(null, "renombrar", null);
                try {
                    rename(file, renombre);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(NavegadorDeArchivos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        organizar.setAction(new AbstractAction("Organizar") {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = (File) jTree1.getLastSelectedPathComponent();
                if (file.isDirectory()) {
                    organizar(file);
                } else {
                    JOptionPane.showMessageDialog(null, "por favor seleccione un folder", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    }
    private void menu(){
        copyItem = new JMenuItem("Copiar");
        cutItem = new JMenuItem("Cortar");
        pasteItem = new JMenuItem("Pegar");
        rename = new JMenuItem("Renombrar");
        refreshyep = new JMenuItem("Refrescar");
        createfile = new JMenuItem("Nuevo Archivo");
        createfolder = new JMenuItem("Nuevo Folder");
        organizar = new JMenuItem("Organizar");
        popupMenu.add(copyItem);
        popupMenu.add(cutItem);
        popupMenu.add(pasteItem);
        popupMenu.add(rename);
        popupMenu.add(refreshyep);
        popupMenu.add(createfile);
        popupMenu.add(createfolder);
        popupMenu.add(organizar);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        buttonGroup8 = new javax.swing.ButtonGroup();
        buttonGroup9 = new javax.swing.ButtonGroup();
        buttonGroup10 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setBorder(new javax.swing.border.MatteBorder(null));
        setClosable(true);
        setForeground(java.awt.Color.lightGray);
        setIconifiable(true);
        setResizable(true);
        setTitle("Navegador De Archivos");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(743, 508));

        jTree1.setBackground(new java.awt.Color(255, 255, 255));
        jTree1.setBorder(new javax.swing.border.MatteBorder(null));
        jTree1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 24)); // NOI18N
        jTree1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTree1FocusGained(evt);
            }
        });
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTree1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));

        jRadioButton1.setText("Ver por Fecha");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setText("Ver por Tamaño");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jRadioButton3.setText("Ver por Nombre");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        jRadioButton4.setText("Ver por Tipo");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Dubai Light", 0, 18)); // NOI18N
        jLabel1.setText("Informacion De Archivos");

        jScrollPane3.setToolTipText("");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
private static String obtenerRutaRelativa(String carpetaBase, File archivo) {
        String rutaBase = new File(carpetaBase).getAbsolutePath();
        String rutaAbsolutaArchivo = archivo.getAbsolutePath();

        if (rutaAbsolutaArchivo.startsWith(rutaBase)) {
  
            String rutaRelativa = rutaAbsolutaArchivo.substring(rutaBase.length());

            if (rutaRelativa.startsWith(File.separator)) {
                rutaRelativa = rutaRelativa.substring(1);
            }

            return rutaRelativa;
        } else {

            return rutaAbsolutaArchivo;
        }
    }
    private void jTree1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTree1FocusGained
        if (refresh == 0) {
            refresh++;
            if (tipo.equals("Administrador")) {
                jTree1.setModel(new BasedeAdminArchivos(new File("Z")));
            } else {
                jTree1.setModel(new BasedeAdminArchivos(new File("Z/" + nombre)));
            }
        }
    }//GEN-LAST:event_jTree1FocusGained

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
    SortbyType();
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
     sortbyName();
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
    sortbyDate();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
     SortbySize();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jTree1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MousePressed
     file = (File) jTree1.getLastSelectedPathComponent();
     
     if(file!=null){
        if(file.exists()&&file.isFile()){
            String ruta = obtenerRutaRelativa("Z/", file);
            long bytes = file.length();
            Date date = new Date(file.lastModified());
            String size="";
            if(bytes<1000){
                size=bytes+" b";
            }
            if(bytes>1000){
                size=bytes/1000+" Kb";
            } 
            if(bytes>1000000){
                size=bytes/1000000+" Mb"; 
            }
            if(bytes>(1000000000)){
                size=bytes/1000000000+" Gb"; 
            }
            
            
        jTextArea1.setText("Nombre de archivo: "+file.getName()+"\nTamaño: "+size +"\nfecha: "+date.toString()+"\nDireccion: "+ruta);
        }
     }
    }//GEN-LAST:event_jTree1MousePressed
    private void copyFile(File file) {
        switch (orden) {
            case "name":
                OrdNombre.copy(file);
                break;
            case "date":
                OrdFecha.copy(file);
                break;
            case "type":
                OrdTipo.copy(file);
                break;
            case "size":
                OrdTamaño.copy(file);
            default:
                ModeloBASE.copy(file);
                break;
        }

    }

    private void cutFile(File file) {
        switch (orden) {
            case "name":
                OrdNombre.Cortar(file);
                break;
            case "date":
                OrdFecha.Cortar(file);
                break;
            case "type":
                OrdTipo.Cortar(file);
                break;
            case "size":
                OrdTamaño.Cortar(file);
                break;
            default:
                ModeloBASE.Cortar(file);
                break;
        }

    }

    private void pasteFile(File destinationFolder) {
        try {
            switch (orden) {
                case "name":
                    OrdNombre.Pegar(destinationFolder);
                    refreshing();
                    break;
                case "date":
                    OrdFecha.Pegar(destinationFolder);
                    refreshing();
                    break;
                case "type":
                    OrdTipo.Pegar(destinationFolder);
                    refreshing();
                    break;
                case "size":
                    OrdTamaño.Pegar(destinationFolder);
                    refreshing();
                    break;
                default:
                    ModeloBASE.Pegar(destinationFolder);
                    refreshing();
                    break;
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR NO SELECCIONO UN FOLDER", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshing() {
        switch (orden) {
            case "name":
                if (tipo.equals("Administrador")) {
                    OrdenarPorNombre model = new OrdenarPorNombre(new File("Z"));
                    model.Actualizar();
                    jTree1.setModel(model);
                } else {
                    OrdenarPorNombre model = new OrdenarPorNombre(new File("Z/" + nombre));
                    model.Actualizar();
                    jTree1.setModel(model);
                }
                break;
            case "date":
                if (tipo.equals("Administrador")) {
                    OrdenarPorFecha model = new OrdenarPorFecha(new File("Z"));
                    model.Actualizar();
                    jTree1.setModel(model);
                } else {
                    OrdenarPorFecha model = new OrdenarPorFecha(new File("Z/" + nombre));
                    model.Actualizar();
                    jTree1.setModel(model);
                }
                break;
            case "type":
                if (tipo.equals("Administrador")) {
                    OrdenarPorTipo model = new OrdenarPorTipo(new File("Z"));
                    model.Actualizar();
                    jTree1.setModel(model);
                } else {
                    OrdenarPorTipo model = new OrdenarPorTipo(new File("Z/" + nombre));
                    model.Actualizar();
                    jTree1.setModel(model);
                }
                break;
            case "size":
                if (tipo.equals("Administrador")) {
                    OrdenarPorTamaño model = new OrdenarPorTamaño(new File("Z"));
                    model.Actualizar();
                    jTree1.setModel(model);
                } else {
                    OrdenarPorTamaño model = new OrdenarPorTamaño(new File("Z/" + nombre));
                    model.Actualizar();
                    jTree1.setModel(model);
                }
                break;
            default:
                if (tipo.equals("Administrador")) {
                    BasedeAdminArchivos model = new BasedeAdminArchivos(new File("Z"));
                    model.Actualizar();
                    jTree1.setModel(model);
                } else {
                    BasedeAdminArchivos model = new BasedeAdminArchivos(new File("Z/" + nombre));
                    model.Actualizar();
                    jTree1.setModel(model);
                }
                break;
        }
    }

    private void sortbyName() {
        orden = "name";
        if (tipo.equals("Administrador")) {
            jTree1.setModel(new OrdenarPorNombre(new File("Z")));
        } else {
            jTree1.setModel(new OrdenarPorNombre(new File("Z/" + nombre)));
        }

    }

    private void sortbyDate() {
        orden = "date";
        if (tipo.equals("Administrador")) {
            jTree1.setModel(new OrdenarPorFecha(new File("Z")));
        } else {
            jTree1.setModel(new OrdenarPorFecha(new File("Z/" + nombre)));
        }
    }

    private void SortbyType() {
        orden = "type";
        if (tipo.equals("Administrador")) {
            jTree1.setModel(new OrdenarPorTipo(new File("Z")));
        } else {
            jTree1.setModel(new OrdenarPorTipo(new File("Z/" + nombre)));
        }
    }

    private void SortbySize() {
        orden = "size";
        if (tipo.equals("Administrador")) {
            jTree1.setModel(new OrdenarPorTamaño(new File("Z")));
        } else {
            jTree1.setModel(new OrdenarPorTamaño(new File("Z/" + nombre)));
        }
    }

    private void newFile(File file) throws IOException {
        switch (orden) {
            case "name":
                OrdNombre.CrearArchivo(file, "New File");
                refreshing();
                break;
            case "date":
                OrdFecha.CrearArchivo(file, "New File");
                refreshing();
                break;
            case "type":
                OrdTipo.CrearArchivo(file, "New File");
                refreshing();
                break;
            case "size":
                OrdTamaño.CrearArchivo(file, "New File");
                refreshing();
                break;
            default:
                ModeloBASE.CrearArchivo(file, "New File");
                refreshing();
                break;
        }

    }

    private void newFolder(File file) throws IOException {
        switch (orden) {
            case "name":
                OrdNombre.CrearFolder(file, "New File");
                refreshing();
                break;
            case "date":
                OrdFecha.CrearFolder(file, "New File");
                refreshing();
                break;
            case "type":
                OrdTipo.CrearFolder(file, "New File");
                refreshing();
                break;
            case "size":
                OrdTamaño.CrearFolder(file, "New File");
                refreshing();
                break;
            default:
                ModeloBASE.CrearFolder(file, "New File");
                refreshing();
                break;
        }
    }

    private void rename(File file, String string) throws IOException {
        switch (orden) {
            case "name":
                OrdNombre.Renombrar(file, string);
                refreshing();
                break;
            case "date":
                OrdFecha.Renombrar(file, string);
                refreshing();
                break;
            case "type":
                OrdTipo.Renombrar(file, string);
                refreshing();
                break;
            case "size":
                OrdTamaño.Renombrar(file, string);
                refreshing();
                break;
            default:
                ModeloBASE.Renombrar(file, string);
                refreshing();
                break;
        }

    }

    private void organizar(File file) {
        switch (orden) {
            case "name":
                OrdNombre.Ordenar(file);
                refreshing();
                break;
            case "date":
                OrdFecha.Ordenar(file);
                refreshing();
                break;
            case "type":
                OrdTipo.Ordenar(file);
                refreshing();
                break;
            case "size":
                OrdTamaño.Ordenar(file);
                refreshing();
                break;
            default:
                ModeloBASE.Ordenar(file);
                refreshing();
                break;
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup10;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
    private javax.swing.ButtonGroup buttonGroup8;
    private javax.swing.ButtonGroup buttonGroup9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
