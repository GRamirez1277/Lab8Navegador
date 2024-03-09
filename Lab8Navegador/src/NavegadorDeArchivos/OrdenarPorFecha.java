package NavegadorDeArchivos;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

public class OrdenarPorFecha extends BasedeAdminArchivos{
    
    public OrdenarPorFecha(File rootDirectory) {
        super(rootDirectory);
    }
    @Override
    public Object getChild(Object parent, int index) {
        File directory = (File) parent;
        File[] children = directory.listFiles();
        Arrays.sort(children, (File f1, File f2) -> Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()));
        return new OrdenarPorFecha.TreeFile(directory, children[index].getName());
    }

    @Override
    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            return file.listFiles().length;
        }
        return 0;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File directory = (File) parent;
        File file = new File(directory, child.toString());
        File[] children = directory.listFiles();
        Arrays.sort(children, (File f1, File f2) -> Long.valueOf(f1.lastModified()).compareTo(f2.lastModified()));
        for (int i = 0; i < children.length; i++) {
            if (file.getName().equals(children[i].getName())) {
                return i;
            }
        }
        return -1;
    }
    private class TreeFile extends File {

        public TreeFile(File parent, String child) {
            super(parent, child);
        }

        @Override
        public String toString() {
            return getName();
        }
    }
    
    @Override
    public boolean Renombrar(File file, String newName) throws IOException {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Archivo o folder no encontrado", "ERROR", JOptionPane.ERROR_MESSAGE);
            throw new FileNotFoundException("");
        }

        File newFile = new File(file.getParent(), newName + ExtensionActual(file));
        if (newFile.exists()) {
            JOptionPane.showMessageDialog(null, "Nombre de archivo Existente", "ERROR", JOptionPane.ERROR_MESSAGE);
            throw new IOException("");

        }

        boolean Renombrado = file.renameTo(newFile);
        
        if (Renombrado) {
            int[] indices = {getIndexOfChild(file.getParentFile(), newFile)};
            Object[] children = {new OrdenarPorFecha.TreeFile(file.getParentFile(), newName)};
            fireTreeNodesChanged(new TreePath(file.getParentFile()), indices, children);
        }
        
        return Renombrado;
    }

    private static String ExtensionActual(File file) {
        String extension = "";
        String fileName = file.getName();
        int PosicionPunto = fileName.lastIndexOf('.');
        if (PosicionPunto > 0 && PosicionPunto < fileName.length() - 1) {
            extension = "." + fileName.substring(PosicionPunto + 1);
        }
        return extension;
    }

}