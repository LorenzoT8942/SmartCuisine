import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaTrash, FaPlus } from "react-icons/fa";
import axios from "axios";
import "../../CSS/shopping-list-page.css"; //Import del file CSS
import ShoppingListCard from "./ShoppingListCard.tsx";

const ShoppingListPage = () => {

  interface ShoppingList {
      username: String;
      name: String;
  }

  interface ShoppingListRequestDTO{
    name: String; 
  }

  const [shoppingLists, setShoppingLists] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newListName, setNewListName] = useState('');
  const token = localStorage.getItem('authToken');
  const navigate = useNavigate();

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
          console.log(data);
      } catch (err) {
          setError(err.message);
      } finally {
          setLoading(false);
      }
  };

  useEffect(() => {
      fetchShoppingLists();
  }, []);


  const handleDelete = async (name) => {
    if(token == null) throw new Error("the token is null");
    const parsedData = JSON.parse(token);
    const tokenParsed = parsedData.token;
    try {
        setLoading(true);
        const response = await axios.delete(`http://localhost:3002/shopping-lists/${name}`, {
            headers: {
                Authorization: `Bearer ${tokenParsed}`
            }
        });
    } catch (err) {
        setError(err.message);
    } finally {
        setLoading(false);
        fetchShoppingLists();
    }
  };

const onCreate = () => {
    setIsModalOpen(true);
};

const handleCreate = async () => {
    // Handle the creation of the new list
    if(token == null) throw new Error("the token is null");
      const parsedData = JSON.parse(token);
      const tokenParsed = parsedData.token;

      const newShoppingList: ShoppingListRequestDTO = {
        name: newListName
    };

    try {
      setLoading(true);
      const response = await axios.post("http://localhost:3002/shopping-lists/create", newShoppingList, {
        headers: {
            Authorization: `Bearer ${tokenParsed}`,
            'Content-Type': 'application/json'
        }
      }
    );
    } catch (err) {
      setError(err.message);
    } finally {
        setLoading(false);
        fetchShoppingLists();
    }
    
    setIsModalOpen(false);
    setNewListName('');
};

const handleCancel = () => {
    setIsModalOpen(false);
    setNewListName('');
};


if (loading) return <div>Loading...</div>;
if (error) return <div>Error: {error}</div>;
return (
    <div className="shopping-list-page-wrapper">
        <button className="create-button" onClick={onCreate}>
            <FaPlus /> Create New List
        </button>
        <div className="shopping-lists-container">
            {shoppingLists.length > 0 ? (
                shoppingLists.map((list, index) => (
                          <ShoppingListCard key={index} list={list} onDelete={handleDelete} />                   
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
