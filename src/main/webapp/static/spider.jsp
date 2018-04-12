<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/8
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>spiderblog</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="http://dreamsky.github.io/main/blog/common/init.css">
    <link rel="stylesheet" type="text/css" href="../css/loader.css">
    <link rel="stylesheet" type="text/css" href="../css/heart.css">
    <link rel="stylesheet" type="text/css" href="../css/input.css">
    <link rel="stylesheet" type="text/css" href="../css/news.css">

    <style type="text/css">
        body {
            background-color: #326696;
            margin: 0px;
            overflow: hidden;
            font-family:Monospace;
            font-size:13px;
            text-align:center;
            font-weight: bold;
            text-align:center;
        }
        a {
            color:#0078ff;
        }
        #im{
            position: absolute;
            left:530px;
            top:240px
        }
        #getContent{
            position: absolute;
            left:400px;
            top:30px
        }
    </style>
</head>
<body>
<div id="im">

    <div class="text-input">
        <input type="text" id="userName" placeholder="请输入用户名">
        <label for="userName" id="spider">Start</label>
    </div>

    <button class="bubbly-button" id="blogNews">Get Latest News!</button>

    <div id="getContent">
        <a href="/crawler/selectBlogsByPage.action" class="button heart"></a>

        <a href="/news/selectNewsByPage.action" class="button flower"></a>
    </div>

    <div class="spinner">
        <div class="spinner-container container1">
            <div class="circle1"></div>
            <div class="circle2"></div>
            <div class="circle3"></div>
            <div class="circle4"></div>
        </div>
        <div class="spinner-container container2">
            <div class="circle1"></div>
            <div class="circle2"></div>
            <div class="circle3"></div>
            <div class="circle4"></div>
        </div>
        <div class="spinner-container container3">
            <div class="circle1"></div>
            <div class="circle2"></div>
            <div class="circle3"></div>
            <div class="circle4"></div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript" src="../js/three.min.js"></script>
<script type="text/javascript" src="../js/Detector.js"></script>
<script id="vs" type="x-shader/x-vertex">

			varying vec2 vUv;

			void main() {

				vUv = uv;
				gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );

			}

		</script>
<script id="fs" type="x-shader/x-fragment">

			uniform sampler2D map;

			uniform vec3 fogColor;
			uniform float fogNear;
			uniform float fogFar;

			varying vec2 vUv;

			void main() {

				float depth = gl_FragCoord.z / gl_FragCoord.w;
				float fogFactor = smoothstep( fogNear, fogFar, depth );

				gl_FragColor = texture2D( map, vUv );
				gl_FragColor.w *= pow( gl_FragCoord.z, 20.0 );
				gl_FragColor = mix( gl_FragColor, vec4( fogColor, gl_FragColor.w ), fogFactor );

			}

		</script>
<script type="text/javascript">

    if ( ! Detector.webgl ) Detector.addGetWebGLMessage();

    var container;
    var camera, scene, renderer;
    var mesh, geometry, material;

    var mouseX = 0, mouseY = 0;
    var start_time = Date.now();

    var windowHalfX = window.innerWidth / 2;
    var windowHalfY = window.innerHeight / 2;

    init();

    function init() {

        container = document.createElement( 'div' );
        document.body.appendChild( container );

        // Bg gradient

        var canvas = document.createElement( 'canvas' );
        canvas.width = 32;
        canvas.height = window.innerHeight;

        var context = canvas.getContext( '2d' );

        var gradient = context.createLinearGradient( 0, 0, 0, canvas.height );
        gradient.addColorStop(0, "#1e4877");
        gradient.addColorStop(0.5, "#4584b4");

        context.fillStyle = gradient;
        context.fillRect(0, 0, canvas.width, canvas.height);

        container.style.background = 'url(' + canvas.toDataURL('image/png') + ')';
        container.style.backgroundSize = '32px 100%';

        //

        camera = new THREE.PerspectiveCamera( 30, window.innerWidth / window.innerHeight, 1, 3000 );
        camera.position.z = 6000;

        scene = new THREE.Scene();

        geometry = new THREE.Geometry();

        var texture = THREE.ImageUtils.loadTexture( '../images/cloud10.png', null, animate );
        texture.magFilter = THREE.LinearMipMapLinearFilter;
        texture.minFilter = THREE.LinearMipMapLinearFilter;

        var fog = new THREE.Fog( 0x4584b4, - 100, 3000 );

        material = new THREE.ShaderMaterial( {

            uniforms: {

                "map": { type: "t", value: texture },
                "fogColor" : { type: "c", value: fog.color },
                "fogNear" : { type: "f", value: fog.near },
                "fogFar" : { type: "f", value: fog.far },

            },
            vertexShader: document.getElementById( 'vs' ).textContent,
            fragmentShader: document.getElementById( 'fs' ).textContent,
            depthWrite: false,
            depthTest: false,
            transparent: true

        } );

        var plane = new THREE.Mesh( new THREE.PlaneGeometry( 64, 64 ) );

        for ( var i = 0; i < 8000; i++ ) {

            plane.position.x = Math.random() * 1000 - 500;
            plane.position.y = - Math.random() * Math.random() * 200 - 15;
            plane.position.z = i;
            plane.rotation.z = Math.random() * Math.PI;
            plane.scale.x = plane.scale.y = Math.random() * Math.random() * 1.5 + 0.5;

            THREE.GeometryUtils.merge( geometry, plane );

        }

        mesh = new THREE.Mesh( geometry, material );
        scene.add( mesh );

        mesh = new THREE.Mesh( geometry, material );
        mesh.position.z = - 8000;
        scene.add( mesh );

        renderer = new THREE.WebGLRenderer( { antialias: false } );
        renderer.setSize( window.innerWidth, window.innerHeight );
        container.appendChild( renderer.domElement );

        document.addEventListener( 'mousemove', onDocumentMouseMove, false );
        window.addEventListener( 'resize', onWindowResize, false );

    }

    function onDocumentMouseMove( event ) {

        mouseX = ( event.clientX - windowHalfX ) * 0.25;
        mouseY = ( event.clientY - windowHalfY ) * 0.15;

    }

    function onWindowResize( event ) {

        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();

        renderer.setSize( window.innerWidth, window.innerHeight );

    }

    function animate() {

        requestAnimationFrame( animate );

        position = ( ( Date.now() - start_time ) * 0.03 ) % 8000;

        camera.position.x += ( mouseX - camera.position.x ) * 0.01;
        camera.position.y += ( - mouseY - camera.position.y ) * 0.01;
        camera.position.z = - position + 8000;

        renderer.render( scene, camera );

    }

</script>
<script src="http://dreamsky.github.io/main/blog/common/jquery.min.js"></script>
<script src="../js/jquery-1.12.0.min.js"></script>
<script src="../js/input.js"></script>
<script src="../js/news.js"></script>
<script src="../js/crawler.js"></script>
</html>
