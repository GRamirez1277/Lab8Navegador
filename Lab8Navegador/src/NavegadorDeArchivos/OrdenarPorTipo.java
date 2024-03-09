package NavegadorDeArchivos;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.TreePath;

public class OrdenarPorTipo extends BasedeAdminArchivos{

    public OrdenarPorTipo(File rootDirectory) {
        super(rootDirectory);
    }

    @Override
    public Object getChild(Object parent, int index) {
        File directory = (File) parent;
        File[] children = directory.listFiles();
        Arrays.sort(children, (File f1, File f2) -> {
            if (f1.isDirectory() && !f2.isDirectory()) {
                return -1;
            } else if (!f1.isDirectory() && f2.isDirectory()) {
                return 1;
            } else {
                return f1.getName().compareToIgnoreCase(f2.getName());
            }
        });
        return new OrdenarPorTipo.TreeFile(directory, children[index].getName());
    }

    @Override
    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            if (fileList != null) {
                return fileList.length;
            }
        }
        return 0;
    }
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File directory = (File) parent;
        File file = new File(directory, child.toString());
        File[] children = directory.listFiles();
        Arrays.sort(children, (File f1, File f2) -> {
            if (f1.isDirectory() && !f2.isDirectory()) {
                return -1;
            } else if (!f1.isDirectory() && f2.isDirectory()) {
                return 1;
            } else {
                return f1.getName().compareToIgnoreCase(f2.getName());
            }
        });
        for (int i = 0; i < children.length; i++) {
            if (file.equals(children[i])) {
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
            JOptionPane.showMessageDialog(null, "Archivo o folder no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
            throw new FileNotFoundException("");
        }

        File newFile = new File(file.getParent(), newName + ExtensionActual(file));
        if (newFile.exists()) {
            JOptionPane.showMessageDialog(null, "Ese Nombre ya existe!!!", "ERROR", JOptionPane.ERROR_MESSAGE);
            throw new IOException("");

        }

        boolean renamed = file.renameTo(newFile);
        if (renamed) {
            int[] indices = {getIndexOfChild(file.getParentFile(), newFile)};
            Object[] children = {new OrdenarPorTipo.TreeFile(file.getParentFile(), newName)};
            fireTreeNodesChanged(new TreePath(file.getParentFile()), indices, children);
        }
        return renamed;
    }

    private static String ExtensionActual(File file) {
        String extension = "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = "." + fileName.substring(dotIndex + 1);
        }
        System.out.println("A:  " + extension);

        return extension;
    }
  
}
