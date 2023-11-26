from sqlalchemy import Column, Integer, String, Date
from config import db

class Receita(db.Model):
    __tablename__ = 'receita'

    id = Column(Integer, primary_key=True)
    descricao = Column(String(255))
    valor = Column(Integer)
    data = Column(Date)

    def __init__(self, descricao, valor, data):
        self.descricao = descricao
        self.valor = valor
        self.data = data

    def serialize(self):
        return {
            'id': self.id,
            'descricao': self.descricao,
            'valor': self.valor,
            'data': str(self.data)
        }

    def atualizar(self, descricao=None, valor=None, data=None):
        if descricao:
            self.descricao = descricao
        if valor:
            self.valor = valor
        if data:
            self.data = data

    def excluir(self):
        db.session.delete(self)
        db.session.commit()
