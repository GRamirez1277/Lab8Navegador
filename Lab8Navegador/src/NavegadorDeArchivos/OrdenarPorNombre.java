package NavegadorDeArchivos;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;


public class OrdenarPorNombre extends BasedeAdminArchivos{
    private Vector<TreeModelListener> listeners = new Vector<>();

    public OrdenarPorNombre(File rootDirectory) {
        super(rootDirectory);
    }

    @Override
    public Object getChild(Object parent, int index) {
        File directory = (File) parent;
        String[] children = directory.list();
        Arrays.sort(children); // sort children by name
        return new TreeFile(directory, children[index]);
    }

    @Override
    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            String[] fileList = file.list();
            return (fileList != null) ? fileList.length : 0;
        }
        return 0;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File directory = (File) parent;
        File file = (File) child;
        String[] children = directory.list();
        Arrays.sort(children); // sort children by name
        for (int i = 0; i < children.length; i++) {
            if (file.getName().equals(children[i])) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        for (TreeModelListener listener : listeners) {
            listener.treeNodesChanged(event);
        }
    }

    private  class TreeFile extends File {

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
            Object[] children = {new OrdenarPorNombre.TreeFile(file.getParentFile(), newName)};
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
        return extension;
    }
}