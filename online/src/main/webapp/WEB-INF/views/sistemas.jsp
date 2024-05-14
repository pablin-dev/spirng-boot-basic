<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>  
        <title>Sistemas</title>  
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="<c:url value='/static/css/app.css' />" rel="stylesheet" />
    </head>
    <body ng-app="sistemaApp" class="ng-cloak">
        <div class="generic-container" ng-controller="SistemaController as ctrl">
            <div class="panel panel-default">
                <div class="panel-heading"><span class="lead">Plantilla de Registro de Sistema</span></div>
                <div class="formcontainer">
                    <form ng-submit="ctrl.submit()" name="sistemaForm" class="form-horizontal">
                        <!--                        <div ng-messages="sistemaForm.$error" role="alert"></div>    -->
                        <input type="hidden" ng-model="ctrl.sistema.id" />
                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="col-md-2 control-lable" for="uname">Nombre</label>
                                <div class="col-md-7">
                                    <input type="text" ng-model="ctrl.sistema.name" id="uname" name="uname" class="username form-control input-sm" placeholder="Ingrese el nombre del Sistema" required ng-minlength="3"/>
                                    <div class="has-error" ng-show="sistemaForm.$dirty">
                                        <span ng-show="sistemaForm.uname.$error.required">This is a required field</span>
                                        <span ng-show="sistemaForm.uname.$error.minlength">Minimum length required is 3</span>
                                        <span ng-show="sistemaForm.uname.$invalid">This field is invalid </span>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="col-md-2 control-lable" for="description">Descripcion</label>
                                <div class="col-md-7">
                                    <input type="text" id="description" ng-model="ctrl.sistema.description" class="form-control input-sm" placeholder="Ingrese descripcion"/>
                                </div>
                            </div>
                        </div>



                        <div class="row">
                            <div class="form-actions floatRight">
                                <input type="submit"  value="{{!ctrl.sistema.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="sistemaForm.$invalid">
                                <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" >Limpiar</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="panel panel-default">
                <!-- Default panel contents -->
                <div class="panel-heading">
                    <span class="lead">Lista de Sistemas </span>
                    <select ng-model="selectedSistema" ng-options="x.name for x in ctrl.sistemas"></select>
                    <textarea style="resize: none;height:18px;width: 500px" rows="1" >{{selectedSistema.description}}</textarea>
                </div>
                <div class="tablecontainer">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>ID.</th>
                                <th>Nombre</th>
                                <th>Descripcion</th>
                                <th width="40%"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-repeat="u in ctrl.sistemas">
                                <td><span ng-bind="u.id"></span></td>
                                <td><span ng-bind="u.name"></span></td>
                                <td><span ng-bind="u.description"></span></td>
                                <td>
                                    <span>
                                        <button type="button" ng-click="ctrl.edit(u.id)" class="btn btn-success custom-width">Editar</button>
                                        <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Eliminar</button>
                                    </span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-messages.min.js"></script>
        <script src="<c:url value='/static/js/app.js' />"></script>
        <script src="<c:url value='/static/js/service/sistema_service.js' />"></script>
        <script src="<c:url value='/static/js/controller/sistema_controller.js' />"></script>
    </body>
</html>