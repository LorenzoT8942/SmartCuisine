import React, { useEffect, useState } from "react";
import { FaTrash, FaPlus } from "react-icons/fa";
import axios from "axios";
import "../../CSS/shopping-list-page.css"; //Import del file CSS
import ShoppingListCard from "./ShoppingListCard.tsx";

const ShoppingListPage = () => {

    interface ShoppingList {
        //finire di definire l'interfaccia
        id: number;
        name: String;
        ingredients : Array<Ingredient>;
    }

    interface Ingredient {
        id: number;
        name: String;
        quantity: number;
        unit: String;
    }

    const [shoppingLists, setShoppingLists] = useState<Array<ShoppingList>>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [newListName, setNewListName] = useState('');
    const token = localStorage.getItem('authToken');

    const fetchShoppingLists = async () => {
        if(token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            setLoading(true);
            const response = await axios.get("http://localhost:3002/shopping-lists", {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
            const data = response.data;
            setShoppingLists(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchShoppingLists();
        console.log(shoppingLists);
    }, []);


    const handleDelete = async (id) => {
      //TODO: Implementare la cancellazione di una lista della spesa
  };

  const onCreate = () => {
      setIsModalOpen(true);
  };

  const handleCreate = () => {
      // Handle the creation of the new list
      setIsModalOpen(false);
      setNewListName('');
  };

  const handleCancel = () => {
      setIsModalOpen(false);
      setNewListName('');
  };

  useEffect(() => {
      fetchShoppingLists();
      console.log(shoppingLists);
  }, []);

  return (
      <div className="shopping-list-page-wrapper">
          <button className="create-button" onClick={onCreate}>
              <FaPlus /> Create New List
          </button>
          <div className="shopping-lists-container">
              {shoppingLists.length > 0 ? (
                  shoppingLists.map((list) => (
                      <ShoppingListCard key={list.id} list={list} onDelete={handleDelete} />
                  ))
              ) : (
                  <div className="no-shopping-lists">No shopping lists found.</div>
              )}
          </div>

          {isModalOpen && (
              <div className="modal">
                  <div className="modal-content">
                      <h2>Create New Shopping List</h2>
                      <input
                          type="text"
                          value={newListName}
                          onChange={(e) => setNewListName(e.target.value)}
                          placeholder="Enter list name"
                      />
                      <button onClick={handleCreate}>Create</button>
                      <button onClick={handleCancel}>Cancel</button>
                  </div>
              </div>
          )}
      </div>
  );
};

export default ShoppingListPage;
