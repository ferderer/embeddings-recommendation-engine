import io
import os
import mariadb
import numpy as np
import tensorflow_hub as hub
from sklearn.metrics.pairwise import cosine_similarity

# Lade TensorFlow Hub Modell
embed = hub.load("https://tfhub.dev/google/universal-sentence-encoder/4")

# Hole Verbindungdaten aus Umgebungsvariablen
conn = mariadb.connect(
    user = os.getenv('ERE_DB_USER'),
    password = os.getenv('ERE_DB_PASS'),
    database = os.getenv('ERE_DB_NAME', 'ere'),
    host = os.getenv('ERE_DB_HOST', '127.0.0.1')
)
cursor = conn.cursor()

# Hole Lektionen aus der Datenbank
cursor.execute("SELECT id, title FROM lesson")
lessons = cursor.fetchall()
lesson_ids = [row[0] for row in lessons]
titles = [row[1] for row in lessons]

# Berechne Embeddings
embeddings = embed(titles).numpy()

# Speichere Embeddings in der Datenbank
for lesson_id, emb in zip(lesson_ids, embeddings):
    emb_bytes = io.BytesIO()
    np.save(emb_bytes, emb)
    cursor.execute(
        "UPDATE lesson SET embedding = %s WHERE id = %s",
        (emb_bytes.getvalue(), lesson_id)
    )
conn.commit()

# Berechne Kosinusähnlichkeiten
similarities = cosine_similarity(embeddings)

# Speichere Ähnlichkeiten in lesson_similarities
for i, id1 in enumerate(lesson_ids):
    for j, id2 in enumerate(lesson_ids):
        if i < j:  # Nur obere Dreiecksmatrix
            cursor.execute(
                "INSERT INTO lesson_similarities (lesson_id1, lesson_id2, similarity) VALUES (%s, %s, %s)",
                (id1, id2, similarities[i, j])
            )

conn.commit()
cursor.close()
conn.close()
