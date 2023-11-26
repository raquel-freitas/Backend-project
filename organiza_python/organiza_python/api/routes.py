from flask import Blueprint, jsonify, request
from app.models import db, Receita
from flask_restx import Api, Resource, fields
import traceback

api_bp = Blueprint('api', __name__)
api = Api(api_bp, version='1.0', title='API de Receitas', description='Uma API do Organiza')

receitas_ns = api.namespace('receitas', description='Operações relacionadas a receitas')

receita_model = api.model('Receita', {
    'id': fields.Integer(required=True, description='ID da receita'),
    'descricao': fields.String(required=True, description='Descrição da receita'),
    'valor': fields.Float(required=True, description='Valor da receita'),
    'data': fields.String(required=True, description='Data da receita'),
})

@receitas_ns.route('/listar')
class ReceitasResource(Resource):
    """Endpoint para listar receitas."""
    @receitas_ns.marshal_with(receita_model, as_list=True)
    def get(self):
        receitas = Receita.query.all()
        return receitas

@receitas_ns.route('/adicionar')
class AdicionarReceitaResource(Resource):
    """Endpoint para adicionar uma nova receita."""
     
    @receitas_ns.expect(receita_model)
    @receitas_ns.marshal_with(receita_model, code=201)
    def post(self):
        try:
            data = request.get_json()

            # Validação
            campos_obrigatorios = ['descricao', 'valor', 'data']
            for campo in campos_obrigatorios:
                if campo not in data:
                    return {"erro": f"Campo obrigatório ausente: {campo}"}, 400

            if not isinstance(data['valor'], (int, float)):
                return {"erro": "O campo 'valor' deve ser um número"}, 400

            nova_receita = Receita(**data)
            db.session.add(nova_receita)
            db.session.commit()
            return nova_receita, 201
        except Exception as e:
            print(f"Erro durante a criação da receita: {str(e)}")
            traceback.print_exc()  # Isso imprimirá a pilha de chamadas para ajudar a identificar o problema
            return {"erro": "Erro interno do servidor"}, 500

@receitas_ns.route('/atualizar/<int:id>')
class AtualizarReceitaResource(Resource):
    """Endpoint para atualizar uma receita por ID."""

    @receitas_ns.expect(receita_model)
    @receitas_ns.marshal_with(receita_model)
    def put(self, id):
        data = request.get_json()
        receita = Receita.query.get(id)
        if receita is None:
            return {'erro': 'Receita não encontrada'}, 404

        # Validação
        campos_obrigatorios = ['descricao', 'valor', 'data']
        for campo in campos_obrigatorios:
            if campo not in data:
                return {"erro": f"Campo obrigatório ausente: {campo}"}, 400

        if not isinstance(data['valor'], (int, float)):
            return {"erro": "O campo 'valor' deve ser um número"}, 400

        receita.atualizar(**data)
        db.session.commit()
        return receita

@receitas_ns.route('/deletar/<int:id>')
class DeletarReceitaResource(Resource):
    """Endpoint para excluir uma receita por ID."""
    @receitas_ns.response(200, 'Receita excluída com sucesso')
    def delete(self, id):
        receita = Receita.query.get(id)
        if not receita:
            return {"message": "Receita não encontrada"}, 404

        db.session.delete(receita)
        db.session.commit()
        return {"message": "Receita excluída com sucesso"}, 200
