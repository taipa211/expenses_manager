package com.expensetracker.manager;

import com.expensetracker.model.Category;
import java.util.ArrayList;
import java.util.List;
/**
 * TODO: CategoryManager
 *
 * Responsabilidade: gerir a lista de categorias de despesas.
 *
 * O que esta classe deve fazer:
 * - Guardar uma lista de categorias em memória
 * - Inicializar com categorias predefinidas (ex: Food, Transport, Entertainment, Other)
 * - Permitir adicionar novas categorias
 * - Permitir apagar uma categoria pelo ID
 * - Permitir listar todas as categorias
 * - Permitir encontrar uma categoria pelo ID
 * - Permitir encontrar uma categoria pelo nome
 *
 */
public class CategoryManager {
    private List<Category> categories;
    private int nextId;

    public CategoryManager() {
this.categories= new ArrayList<>();
this.nextId=1;
// Inicializar com categorias predefinidas
add("Food");
add("Transport");
add("Entertainment");
add("Health");
add("Clothes");
add  ("Shopping");
add ("Utilites");
add ("Subscriptions");
add ("Education");
add ("Other");


    }

    public Category add(String name) {
        if (name.isEmpty() || name.isBlank() || name.matches(".*\\d.*") || findByName(name) != null) {
            System.out.println("Invalid category name,please try again.");
            return null;
        }
 Category category = new Category(nextId++, name);
categories.add(category);
return category;

    }

    public boolean delete(int id) { 
      return   categories.removeIf(c -> c.getId() == id);}
    public List<Category> list() {
        return new ArrayList<>(categories);
     }
    public Category findById(int id) { 
        if (id <= 0) {
            System.out.println("Invalid category ID, please try again.");
            return null;    
        }
        else {
            return categories.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        }
        
        
     }
    public Category findByName(String name) { return categories.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null); }
}
