import axios from 'axios';
import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import '../../CSS/shopping-list-details.css';
import { FaArrowLeft, FaPlusCircle, FaTrash } from 'react-icons/fa';
import IngredientModal from './IngredientModal.tsx';
import IngredientCard from '../IngredientCard.tsx';

interface Ingredient {
    id: number;
    name: String;
    quantity: number;
}

interface IngredientDTO {
    ingredientId: number;
    quantity: number;
}

interface ShoppingList {
    username: string;
    name: String;
    //ingredients: Array<IngredientDTO>;
    ingredients: { [key: number]: number };
}


const ShoppingListDetails = () => {
    const { listName } = useParams();
    const [shoppingList, setShoppingList] = useState<ShoppingList | null>(null);
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const token = localStorage.getItem('authToken');
    const navigate = useNavigate();

    const isListEmpty = () => {
        if (shoppingList && shoppingList.ingredients) {
            return Object.entries(shoppingList.ingredients).length === 0;
        }

        return false;
    }

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
            }).then((response) => {
                setShoppingList(response.data);
            });
            
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const fetchAllIngredients = async () => {
        if (shoppingList && shoppingList.ingredients) {
            const ingredientDetails = await Promise.all(
                Object.entries(shoppingList.ingredients).map(async ([ingredientId, quantity]) => {
                    const ingredientDetails = await fetchIngredientDetails(Number(ingredientId));
                    if (ingredientDetails) {
                        return {
                            id: Number(ingredientId),
                            name: ingredientDetails.name,
                            quantity: quantity
                        };
                    }
                    return null;
                })
            );
            console.log("pazzesco");
            console.log(ingredientDetails);
            setIngredients(ingredientDetails.filter((ingredient) => ingredient !== null) as Ingredient[]);
        }
    };

    const fetchIngredientDetails = async (ingredientId: number) => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            const response = await axios.get(`http://localhost:3004/recipes-ingredients/ingredients/search-by-id/${ingredientId}`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
            return response.data;
        } catch (err) {
            setError('Error fetching ingredient details');
            return null;
        }
    };

    const handleAddIngredientClick= () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleAddIngredientToList = () => {
        fetchShoppingList();
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

    const handleMoveIngredientsToStorage = async () => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            await axios.post(`http://localhost:3005/api/storage/move-ingredients/${listName}`,{}, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            });
        } catch (err) {
            console.log(err);
            setError(err.message);
        }
    }

    const handleDeleteIngredient = async (ingredientId: number) => {
        if (token == null) throw new Error("the token is null");
        const parsedData = JSON.parse(token);
        const tokenParsed = parsedData.token;
        try {
            await axios.delete(`http://localhost:3002/shopping-lists/${listName}/delete-ingredient/${ingredientId}`, {
                headers: {
                    Authorization: `Bearer ${tokenParsed}`
                }
            }).then(() => {    
                    fetchShoppingList();
            });
            
        } catch (err) {
            setError(err.message);
        }
    }

    useEffect(() => {
        fetchShoppingList();
        console.log("ingredients", ingredients);

    }, [listName, token]);

    useEffect(() => {
        console.log("ingredients", ingredients);
    }, [ingredients]);

    useEffect(() => {
        fetchAllIngredients();
    }, [shoppingList, token]);

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
                    <button
                        className="action-button"
                        onClick={() => {
                            handleMoveIngredientsToStorage();
                            alert("Moved to storage correctly");
                        }}
                    >
                        Move to Storage
                    </button>
                </div>
            </div>
            
            <h1 className="center-text">{listName}</h1>

            <hr />
            { ingredients.length > 0? (
                <div className="ingredients-list">
                {ingredients.map((ingredient) => (
                    <IngredientCard key={ingredient.id} id={ingredient.id} name={ingredient.name as string} quantity={ingredient.quantity} expirationDate={null} onDelete={handleDeleteIngredient} />
                ))}
            </div>
            ) : (
                <h1 className="center-text">No ingredients in this shopping list</h1>
            )}
            {isModalOpen && (
                <IngredientModal onClose={handleCloseModal} onAdd={handleAddIngredientToList} listName={listName} />
            )}
            
        </div>
    );
};

export default ShoppingListDetails;