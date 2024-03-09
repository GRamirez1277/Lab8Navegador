package NavegadorDeArchivos;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.TreePath;

public class OrdenarPorTamaño extends BasedeAdminArchivos{
    
    public OrdenarPorTamaño(File rootDirectory) {
        super(rootDirectory);
    }
    
    @Override
    public Object getChild(Object parent, int index) {
        File directory = (File) parent;
        File[] children = directory.listFiles();
        Arrays.sort(children, new SizeComparator()); // sort by file size
        return new TreeFile(directory, children[index].getName());
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
        File file = (File) child;
        File[] children = directory.listFiles();
        Arrays.sort(children, new SizeComparator()); // sort by file size
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

    private class SizeComparator implements Comparator<File> {

        @Override
        public int compare(File file1, File file2) {
            long size1 = file1.length();
            long size2 = file2.length();
            if (size1 < size2) {
                return -1;
            } else if (size1 == size2) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public void sortByName(File root) {
        File[] files = root.listFiles();
        Arrays.sort(files, (file1, file2) -> file1.getName().compareTo(file2.getName()));
        for (File file : files) {
            if (file.isDirectory()) {
                sortByName(file);
            }
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
            Object[] children = {new OrdenarPorTamaño.TreeFile(file.getParentFile(), newName)};
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
