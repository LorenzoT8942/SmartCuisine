import axios from 'axios';
import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import '../../CSS/shopping-list-details.css';
import { FaArrowLeft, FaPlusCircle, FaTrash } from 'react-icons/fa';
import IngredientModal from './IngredientModal.tsx';

interface Ingredient {
    id: number;
    name: String;
    quantity: number;
    unit: String;
}

interface ShoppingList {
    id: number;
    name: String;
    ingredients: Array<Ingredient>;
}

const ShoppingListDetails = () => {
    const { listName } = useParams();
    const [shoppingList, setShoppingList] = useState<ShoppingList | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const token = localStorage.getItem('authToken');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchShoppingList = async () => {
            if (token == null) throw new Error("the token is null");
            const parsedData = JSON.parse(token);
            const tokenParsed = parsedData.token;
            try {
                setLoading(true);
                const response = await axios.get(`http://localhost:3002/shopping-lists/${listName}`, {
                    headers: {
                        Authorization: `Bearer ${tokenParsed}`
                    }
                });
                setShoppingList(response.data);
                console.log("shopping list fetched:")
                console.log(response.data);
                console.log(shoppingList)
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchShoppingList();
    }, [listName, token]);

    const handleAddIngredientClick= () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleAddIngredientToList = (ingredient: Ingredient) => {
        // Logica per aggiungere l'ingrediente alla lista
        console.log("Ingredient added:", ingredient);
        setIsModalOpen(false);
    };

    const handleDeleteList = async () => {
        // Logica per eliminare la lista
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            await axios.delete(`http://localhost:3002/shopping-lists/${listName}`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
            navigate(-1); // Torna alla pagina precedente dopo l'eliminazione
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    return (
        <div className="shopping-list-wrapper">
            <div className="header">
                <button className="back-button" onClick={() => navigate(-1)}>
                    <FaArrowLeft /> Back
                </button>
                <div className="actions">
                    <button className="action-button" onClick={handleAddIngredientClick}>
                        <FaPlusCircle className="icon" /> Add Ingredient
                    </button>
                    <button className="action-button" onClick={handleDeleteList}>
                        <FaTrash className="icon" /> Delete List
                    </button>
                </div>
            </div>
            <h1 className="center-text">{listName}</h1>
            <hr />
            {shoppingList && shoppingList.ingredients && shoppingList.ingredients.length > 0 ? ( 
                <div>
                    <ul>
                        {shoppingList?.ingredients.map((ingredient) => (
                            <li key={ingredient.id}>
                                {ingredient.name}: {ingredient.quantity} {ingredient.unit}
                            </li>
                        ))}
                    </ul>
                </div>
            ) : (
                <h1 className="center-text">No ingredients in this shopping list</h1>
            )}
            {isModalOpen && (
                <IngredientModal onClose={handleCloseModal} onAdd={handleAddIngredientToList} />
            )}
        </div>
    );
};

export default ShoppingListDetails;