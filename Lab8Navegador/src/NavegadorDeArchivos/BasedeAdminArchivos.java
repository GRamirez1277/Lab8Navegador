package NavegadorDeArchivos;

import NavegadorDeArchivos.NavegadorDeArchivos;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class BasedeAdminArchivos implements TreeModel {

    protected File ubicacionRaiz;
    protected List<Object> List = new ArrayList<>();
    protected File Archivocopy;
    protected boolean isCut;

    public BasedeAdminArchivos(File rootDirectory) {
        ubicacionRaiz = rootDirectory;
    }

    @Override
    public Object getRoot() {
        return ubicacionRaiz;
    }

    @Override
    public Object getChild(Object parent, int index) {
        File directory = (File) parent;
        String[] children = directory.list();
        return new BasedeAdminArchivos.TreeFile(directory, children[index]);
    }

    @Override
    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            String[] fileList = file.list();

            if (fileList != null) {
                return file.list().length;
            }
        }
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        File file = (File) node;
        return file.isFile();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File directory = (File) parent;
        File file = (File) child;
        String[] children = directory.list();
        for (int i = 0; i < children.length; i++) {
            if (file.getName().equals(children[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object value) {
        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) value;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = {getIndexOfChild(parent, targetFile)};
        Object[] changedChildren = {targetFile};
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);
    }

    public void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        Iterator iterator = List.iterator();
        TreeModelListener listener;
        while (iterator.hasNext()) {
            listener = (TreeModelListener) iterator.next();
            listener.treeNodesChanged(event);
        }
    }

    @Override
    public void addTreeModelListener(TreeModelListener listener) {
        List.add(listener);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener listener) {
        List.remove(listener);
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

    public void copy(File file) {
        Archivocopy = file;
        isCut = false;
    }

    public void Cortar(File file) {
        Archivocopy = file;
        isCut = true;
    }

    public void Pegar(File destinationFolder) throws IOException {
        if (Archivocopy == null) {
            JOptionPane.showMessageDialog(null, "Error", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!destinationFolder.isDirectory()) {
            JOptionPane.showMessageDialog(null, "Debe selecionar un folder", "ERROR", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("");
        }
        if (!destinationFolder.exists()) {
            JOptionPane.showMessageDialog(null, "Ese Destino no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
            throw new IOException("");
        }
        if (destinationFolder.equals(Archivocopy.getParentFile())) {
            return;
        }
        File targetFile = new File(destinationFolder, Archivocopy.getName());
        if (isCut) {
            Files.move(Archivocopy.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            Files.copy(Archivocopy.toPath(), targetFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        }
        Archivocopy = null;
    }

    public void Actualizar() {
        int[] indices = {0};
        Object[] children = {ubicacionRaiz};
        fireTreeNodesChanged(new TreePath(ubicacionRaiz), indices, children);
    }

    public boolean CrearFolder(File parent, String folderName) {
        File newFolder = new File(parent, folderName);
        return newFolder.mkdir();
    }

    public boolean CrearArchivo(File parent, String fileName) throws IOException {
        File newFile = new File(parent, fileName);
        return newFile.createNewFile();
    }

    public boolean Renombrar(File file, String newName) throws IOException {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "El archivo o carpeta no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
            throw new FileNotFoundException("");
        }

        File newFile = new File(file.getParent(), newName + ExtensionActual(file));
        if (newFile.exists()) {
            JOptionPane.showMessageDialog(null, "Ese Nombre No esta disponible", "ERROR", JOptionPane.ERROR_MESSAGE);
            throw new IOException("");

        }

        boolean renamed = file.renameTo(newFile);
        if (renamed) {
            int[] indices = {getIndexOfChild(file.getParentFile(), newFile)};
            Object[] children = {new TreeFile(file.getParentFile(), newName)};
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

    public void Ordenar(File directorio) {
        File fileroot = new File("Z/" + NavegadorDeArchivos.nombre + "/");
        File imageDir = new File(fileroot, "Mis Imagenes");
        File docDir = new File(fileroot, "Mis Documentos");
        File musicDir = new File(fileroot, "Mi Musica");

        File[] files = fileroot.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String extension = GetExtension(file.getName());
                if (extension != null) {
                    extension = extension.toLowerCase();

                    switch (extension) {
                        case "jpg":
                        case "png":
                        case "gif":
                            MoverArchivo(file, imageDir);
                            break;
                        case "doc":
                        case "docx":
                        case "pdf":
                        case "txt":
                            MoverArchivo(file, docDir);
                            break;
                        case "mp3":
                        case "wav":
                        case "flac":
                            MoverArchivo(file, musicDir);
                            break;
                        default:

                            break;
                    }
                }
            }
        }
    }

    private void MoverArchivo(File file, File destDir) {
        File destFile = new File(destDir, file.getName());
        if (!destFile.exists()) {
            file.renameTo(destFile);
        }
    }

    private String GetExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return null;
        }
    }
}
