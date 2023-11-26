from app import app, db

# Criação do banco de dados
with app.app_context():
    db.create_all()


if __name__ == '__main__':
    app.run(debug=True)
