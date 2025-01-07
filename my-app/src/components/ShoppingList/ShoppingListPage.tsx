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

    const handleDelete = async (id) => {
        //TODO: Implementare la cancellazione di una lista della spesa
    };

    const onCreate = () => {

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
        
      </div>
    );
};

export default ShoppingListPage;
