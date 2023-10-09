var __extends = this && this.__extends || function(t, e) {
        function i() {
            this.constructor = t
        }
        for (var s in e) e.hasOwnProperty(s) && (t[s] = e[s]);
        t.prototype = null === e ? Object.create(e) : (i.prototype = e.prototype, new i)
    },
    JumperBoyAdventures;
! function(t) {
    var e = function() {
        function t() {}
        return t.game = null, t.GAME_WIDTH = 1024, t.GAME_HEIGHT = 640, t
    }();
    t.Global = e
}(JumperBoyAdventures || (JumperBoyAdventures = {})), window.onload = function() {
    var t = new JumperBoyAdventures.Game;
    JumperBoyAdventures.Global.game = t
};
var JumperBoyAdventures;
! function(t) {
    var e = function(e) {
        function i() {
            e.call(this, t.Global.GAME_WIDTH, t.Global.GAME_HEIGHT, Phaser.AUTO, "content"), t.Preferences.instance.load(), this.state.add("Boot", t.Boot), this.state.add("Preload", t.Preload), this.state.add("Play", t.Play), this.state.add("Menu", t.Menu), this.state.start("Boot")
        }
        return __extends(i, e), i
    }(Phaser.Game);
    t.Game = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function() {
        function t() {}
        return t.SPIKE_ANIM = ["Spikes1", "Spikes2", "Spikes3", "Spikes4", "Spikes3", "Spikes2"], t.BONUS_JUMP_ANIM = ["Bonus0", "Bonus1", "Bonus2", "Bonus3", "Bonus4", "Bonus4", "Bonus4", "Bonus3", "Bonus2", "Bonus1", "Bonus0", "Bonus5", "Bonus6", "Bonus7", "Bonus8", "Bonus8", "Bonus8", "Bonus7", "Bonus6", "Bonus5"], t
    }();
    t.Animations = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(t) {
        function e(e, i) {
            t.call(this, e, i), this._nextTree = 0;
            var s = e.cache.getImage("TreesBg").height,
                n = e.cache.getImage("Hill").height,
                r = e.cache.getImage("Mud").height;
            this._treesBg = new Phaser.TileSprite(e, 0, 0, e.width, s, "TreesBg"), this._treesBg.fixedToCamera = !0, this.add(this._treesBg), this._trees = new Phaser.Group(e, this), this._trees.createMultiple(4, "Sprites", "Tree", !1), this._treeWidth = e.cache.getFrameByName("Sprites", "Tree").width, this._hill = new Phaser.TileSprite(e, 0, e.height - r - n, e.width, n, "Hill"), this._hill.fixedToCamera = !0, this.add(this._hill), this._mud = new Phaser.TileSprite(e, 0, e.height - r, e.width, r, "Mud"), this._mud.fixedToCamera = !0, this.add(this._mud)
        }
        return __extends(e, t), e.prototype.updateLayers = function(t) {
            this._mud.tilePosition.x = -t + 20 * Math.sin(Phaser.Math.degToRad(this.game.time.time / 30 % 360)), this._hill.tilePosition.x = .5 * -t, this._treesBg.tilePosition.x = .25 * -t, this.manageTrees(.5 * t)
        }, e.prototype.manageTrees = function(t) {
            for (this._trees.x = t, this._trees.forEachExists(function(e) {
                    e.x < t - this._treeWidth && (e.exists = !1)
                }, this); this._nextTree < t + this.game.width;) {
                var i = this._nextTree;
                this._nextTree += this.game.rnd.integerInRange(e.TREE_DIST_MIN, e.TREE_DIST_MAX);
                var s = this._trees.getFirstExists(!1);
                if (null === s) break;
                s.x = i, s.exists = !0
            }
        }, e.prototype.resize = function() {
            var t = this.game.width;
            this._treesBg.width = t, this._hill.width = t, this._mud.width = t
        }, e.TREE_DIST_MIN = 300, e.TREE_DIST_MAX = 800, e
    }(Phaser.Group);
    t.Background = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function() {
        function t() {}
        return t.PLATFORM = [
            [{
                name: "PlatformLeft",
                anchorX: 0,
                anchorY: .2,
                bodyOffsetX: 0,
                bodyOffsetY: 16,
                bodyWidth: 64,
                bodyHeight: 48
            }, {
                name: "PlatformMiddle",
                anchorX: 0,
                anchorY: .2,
                bodyOffsetX: 0,
                bodyOffsetY: 16,
                bodyWidth: 64,
                bodyHeight: 48
            }, {
                name: "PlatformRight",
                anchorX: 0,
                anchorY: .2,
                bodyOffsetX: 0,
                bodyOffsetY: 16,
                bodyWidth: 64,
                bodyHeight: 48
            }]
        ], t.BLOCK = [
            [{
                name: "BlockTopLeft",
                anchorX: 0,
                anchorY: .2,
                bodyOffsetX: 0,
                bodyOffsetY: 16,
                bodyWidth: 64,
                bodyHeight: 64
            }, {
                name: "BlockTopMiddle",
                anchorX: 0,
                anchorY: .2,
                bodyOffsetX: 0,
                bodyOffsetY: 16,
                bodyWidth: 64,
                bodyHeight: 64
            }, {
                name: "BlockTopRight",
                anchorX: 0,
                anchorY: .2,
                bodyOffsetX: 0,
                bodyOffsetY: 16,
                bodyWidth: 64,
                bodyHeight: 64
            }],
            [{
                name: "BlockMiddleLeft",
                anchorX: 0,
                anchorY: 0,
                bodyOffsetX: 0,
                bodyOffsetY: 0,
                bodyWidth: 64,
                bodyHeight: 64
            }, {
                name: "BlockMiddleMiddle",
                anchorX: 0,
                anchorY: 0,
                bodyOffsetX: 0,
                bodyOffsetY: 0,
                bodyWidth: 64,
                bodyHeight: 64
            }, {
                name: "BlockMiddleRight",
                anchorX: 0,
                anchorY: 0,
                bodyOffsetX: 0,
                bodyOffsetY: 0,
                bodyWidth: 64,
                bodyHeight: 64
            }],
            [{
                name: "BlockBottomLeft",
                anchorX: 0,
                anchorY: 0,
                bodyOffsetX: 0,
                bodyOffsetY: 0,
                bodyWidth: 64,
                bodyHeight: 64
            }, {
                name: "BlockBottomMiddle",
                anchorX: 0,
                anchorY: 0,
                bodyOffsetX: 0,
                bodyOffsetY: 0,
                bodyWidth: 64,
                bodyHeight: 64
            }, {
                name: "BlockBottomRight",
                anchorX: 0,
                anchorY: 0,
                bodyOffsetX: 0,
                bodyOffsetY: 0,
                bodyWidth: 64,
                bodyHeight: 64
            }]
        ], t.LOW_BLOCK = [
            [{
                name: "LowBlockLeft",
                anchorX: 0,
                anchorY: .2,
                bodyOffsetX: 0,
                bodyOffsetY: 16,
                bodyWidth: 64,
                bodyHeight: 64
            }, {
                name: "LowBlockMiddle",
                anchorX: 0,
                anchorY: .2,
                bodyOffsetX: 0,
                bodyOffsetY: 16,
                bodyWidth: 64,
                bodyHeight: 64
            }, {
                name: "LowBlockRight",
                anchorX: 0,
                anchorY: .2,
                bodyOffsetX: 0,
                bodyOffsetY: 16,
                bodyWidth: 64,
                bodyHeight: 64
            }]
        ], t.SPIKES = {
            name: "spikes",
            anchorX: .5,
            anchorY: 1,
            bodyOffsetX: 9,
            bodyOffsetY: 17,
            bodyWidth: 45,
            bodyHeight: 34
        }, t.BONUS_JUMP = {
            name: "bonusJump",
            anchorX: .5,
            anchorY: .5,
            bodyOffsetX: 7,
            bodyOffsetY: 7,
            bodyWidth: 50,
            bodyHeight: 50
        }, t.GOLD = {
            name: "Gold",
            anchorX: .5,
            anchorY: 1,
            bodyOffsetX: 0,
            bodyOffsetY: 0,
            bodyWidth: 43,
            bodyHeight: 43
        }, t
    }();
    t.BlockDefs = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(t) {
        function e() {
            t.apply(this, arguments)
        }
        return __extends(e, t), e
    }(Phaser.Sprite);
    t.Item = e;
    var i = function(i) {
        function s(n, r) {
            i.call(this, n, r), this._timeForGold = s.GOLD_COUNTER_MIN, this._lastTile = new Phaser.Point(0, 0), this._generator = new Generator.Generator(n.rnd), this._generator.onRandomPlatform.add(this.onRandomPlatform, this), this._generator.onPatternPlatform.add(this.onPatternPlatform, this), this._difficulty = new Generator.Difficulty(n.rnd), this._wallsPool = new Helper.Pool(Phaser.Sprite, 32, function() {
                var t = new Phaser.Sprite(n, 0, 0, "Sprites");
                n.physics.enable(t, Phaser.Physics.ARCADE);
                var e = t.body;
                return e.allowGravity = !1, e.immovable = !0, e.moves = !1, e.setSize(64, 64, 0, 0), t
            }), this._walls = new Phaser.Group(n, this), this._itemsPool = new Helper.Pool(e, 32, function() {
                var i = new e(n, 0, 0, "Sprites");
                i.animations.add("spikes", t.Animations.SPIKE_ANIM, 10, !0), i.animations.add("bonusJump", t.Animations.BONUS_JUMP_ANIM, 10, !0), n.physics.enable(i, Phaser.Physics.ARCADE);
                var s = i.body;
                return s.allowGravity = !1, s.immovable = !0, s.moves = !1, i
            }), this._items = new Phaser.Group(n, this);
            var a = t.Preferences.instance.record;
            if (a > 0) {
                var o = new t.Record(n, this, a);
                this.add(o)
            }
            this.constructEmitters(), this._generator.setPiece(0, 5, 10), this._state = 0
        }
        return __extends(s, i), s.prototype.render = function() {
            this._items.forEachExists(function(t) {
                this.game.debug.body(t)
            }, this)
        }, s.prototype.constructEmitters = function() {
            var t = new Phaser.Particles.Arcade.Emitter(this.game, 0, 0, 5);
            t.makeParticles("Sprites", "Bonus0"), t.setXSpeed(0, 0), t.setYSpeed(0, 0), t.setRotation(-360, -360), t.lifespan = 500, t.setScale(1, 0, 1, 0, 500), t.gravity = -Generator.Parameters.GRAVITY, this.add(t), this._bonusEmitter = t, t = new Phaser.Particles.Arcade.Emitter(this.game, 0, 0, 48), t.makeParticles("Sprites", "GoldParticle"), t.setXSpeed(-150, 150), t.setYSpeed(-200, 50), t.setRotation(0, 0), t.lifespan = 550, t.setScale(1, 0, 1, 0, 550), t.gravity = -Generator.Parameters.GRAVITY, this.add(t), this._goldEmitter = t
        }, s.prototype.generate = function(t) {
            this.cleanTiles(t), this.cleanItems(t);
            for (var e = Math.ceil(this.game.width / Generator.Parameters.CELL_SIZE); this._lastTile.x < t + e;) switch (this._state) {
                case 0:
                    !this._generator.hasPieces;
                    var i = this._generator.getPieceFromQueue();
                    this._lastTile.copyFrom(i.position);
                    var n = i.length,
                        r = 0;
                    for (i.bonusJump || 0 !== i.spikesPattern || --this._timeForGold; n > 0;) i.bonusJump ? this.addBonus(this._lastTile.x, this._lastTile.y) : (this.addTiles(this._lastTile.x, this._lastTile.y, r, i.isPlatform, (i.spikesPattern & 1 << n - 1) > 0), 0 === this._timeForGold && n > 1 && this.addGold(this._lastTile.x, this._lastTile.y)), --n > 0 && ++this._lastTile.x, r = 1 === n ? 2 : 1;
                    0 === this._timeForGold && (this._timeForGold = this.game.rnd.integerInRange(s.GOLD_COUNTER_MIN, s.GOLD_COUNTER_MAX)), this._generator.destroyPiece(i), this._generator.hasPieces || (this._state = 1);
                    break;
                case 1:
                    this._difficulty.update(t), this._generator.generatePieces(this._lastTile, this._difficulty), this._state = 0
            }
        }, s.prototype.cleanTiles = function(t) {
            t *= Generator.Parameters.CELL_SIZE;
            for (var e = this._walls.length - 1; e >= 0; e--) {
                var i = this._walls.getChildAt(e);
                i.x - t <= -64 && (this._walls.remove(i), i.parent = null, this._wallsPool.destroyItem(i))
            }
        }, s.prototype.cleanItems = function(t) {
            t *= Generator.Parameters.CELL_SIZE;
            for (var e = this._items.length - 1; e >= 0; e--) {
                var i = this._items.getChildAt(e);
                i.x - t <= -64 && (this._items.remove(i), i.parent = null, this._itemsPool.destroyItem(i))
            }
        }, s.prototype.addTiles = function(e, i, s, n, r) {
            var a;
            a = n ? t.BlockDefs.PLATFORM : i === Generator.Parameters.LBOUND ? t.BlockDefs.LOW_BLOCK : t.BlockDefs.BLOCK;
            for (var o = n ? 1 : Generator.Parameters.LBOUND - i + 1, h = i; i + o > h; h++) {
                var u = void 0;
                u = a !== t.BlockDefs.BLOCK ? a[0][s] : h === i ? a[0][s] : i + o - 1 > h ? a[1][s] : a[2][s];
                var l = this._wallsPool.createItem();
                l.position.set(64 * e, 64 * h), l.exists = !0, l.visible = !0, l.frameName = u.name, l.anchor.set(u.anchorX, u.anchorY);
                var _ = l.body;
                if (_.setSize(u.bodyWidth, u.bodyHeight, u.bodyOffsetX, u.bodyOffsetY), null === l.parent && this._walls.add(l), r && h === i) {
                    var c = this._itemsPool.createItem();
                    c.itemType = 0, c.position.set(64 * e + 32, 64 * h + 8), c.exists = !0, c.visible = !0, this.setupItem(c, t.BlockDefs.SPIKES, !0, !0), null === c.parent && this._items.add(c)
                }
            }
        }, s.prototype.addBonus = function(e, i) {
            var s = this._itemsPool.createItem();
            s.itemType = 1, s.position.set(64 * e + 32, 64 * i), s.exists = !0, s.visible = !0, this.setupItem(s, t.BlockDefs.BONUS_JUMP, !0, !1), null === s.parent && this._items.add(s)
        }, s.prototype.addGold = function(e, i) {
            var s = this._itemsPool.createItem();
            s.itemType = 2, s.position.set(64 * e + 64, 64 * i + 8), s.exists = !0, s.visible = !0, this.setupItem(s, t.BlockDefs.GOLD, !1, !1), null === s.parent && this._items.add(s)
        }, s.prototype.removeItem = function(t) {
            1 === t.itemType ? (this._bonusEmitter.emitX = t.x, this._bonusEmitter.emitY = t.y, this._bonusEmitter.emitParticle()) : 2 === t.itemType && (this._goldEmitter.emitX = t.x, this._goldEmitter.emitY = t.y - 25, this._goldEmitter.explode(500, 12)), this._items.remove(t), t.parent = null, this._itemsPool.destroyItem(t)
        }, s.prototype.setupItem = function(t, e, i, s) {
            if (t.anchor.set(e.anchorX, e.anchorY), t.body.setSize(e.bodyWidth, e.bodyHeight, e.bodyOffsetX, e.bodyOffsetY), i) {
                if (t.animations.play(e.name), s) {
                    var n = this.getItemOfType(t.itemType);
                    null !== n && (t.animations.currentAnim._frameIndex = n.animations.currentAnim._frameIndex, t.animations.currentAnim._timeNextFrame = n.animations.currentAnim._timeNextFrame)
                }
            } else t.animations.stop(), t.frameName = e.name
        }, s.prototype.getItemOfType = function(t) {
            for (var e = 0; e < this._items.length; e++) {
                var i = this._items.getChildAt(e);
                if (i.itemType === t) return i
            }
            return null
        }, Object.defineProperty(s.prototype, "walls", {
            get: function() {
                return this._walls
            },
            enumerable: !0,
            configurable: !0
        }), Object.defineProperty(s.prototype, "items", {
            get: function() {
                return this._items
            },
            enumerable: !0,
            configurable: !0
        }), s.prototype.onRandomPlatform = function(t, e) {
            this.setPlatform(t)
        }, s.prototype.onPatternPlatform = function(t, e, i, s, n) {
            0 === i && 0 === s ? this.setPlatform(t) : 0 === s ? this.game.rnd.integerInRange(0, 99) < 50 ? t.isPlatform = e.isPlatform : this.setPlatform(t) : this.game.rnd.integerInRange(0, 99) < 90 ? t.isPlatform = n.isPlatform : this.setPlatform(t)
        }, s.prototype.setPlatform = function(t) {
            var e = 100 - 20 * (t.position.y - Generator.Parameters.UBOUND);
            t.isPlatform = this.game.rnd.integerInRange(0, 99) < e
        }, s.GOLD_COUNTER_MIN = 3, s.GOLD_COUNTER_MAX = 6, s
    }(Phaser.Group);
    t.MainLayer = i
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(e) {
        function i(t) {
            e.call(this, t, 0, 0, "Sprites", "Empty"), this.anchor.x = .5, t.physics.arcade.enable(this, !1);
            var i = this.body;
            i.allowGravity = !0;
            var s = Generator.Parameters.PLAYER_BODY_WIDTH,
                n = Generator.Parameters.PLAYER_BODY_HEIGHT,
                r = -s / 2 + this.width * this.anchor.x,
                a = 0;
            i.setSize(s, n, r, a);
            var o = new Spriter.Loader,
                h = new Spriter.SpriterXml(t.cache.getXML("JumperBoyAnim")),
                u = o.load(h);
            this._spriterGroup = new Spriter.SpriterGroup(this.game, u, "Sprites", "JumperBoy", "fall", 120), this._spriterGroup.position.set(-5, 60), this.addChild(this._spriterGroup);
            var l = new Phaser.Particles.Arcade.Emitter(t, 10, 60, 20);
            l.makeParticles("Sprites", ["MudParticle0", "MudParticle1"]), l.setXSpeed(-100, 100), l.setYSpeed(-500, -200), l.gravity = -Generator.Parameters.GRAVITY + 800, this.addChild(l), this._mudEmitter = l
        }
        return __extends(i, e), Object.defineProperty(i.prototype, "animName", {
            get: function() {
                return this._spriterGroup.currentAnimationName
            },
            enumerable: !0,
            configurable: !0
        }), i.prototype.animateJump = function() {
            this._spriterGroup.playAnimationByName("jump"), t.Sounds.sfx.play("jump")
        }, i.prototype.animateDeath = function() {
            var e = this.body;
            e.enable = !1, this._spriterGroup.playAnimationByName("fall_water"), t.Sounds.sfx.play("mud_fall"), this.game.time.events.add(1e3, function() {
                t.Sounds.sfx.play("end")
            }, this), this._mudEmitter.explode(0, 20)
        }, i.prototype.animateHit = function() {
            this._spriterGroup.playAnimationByName("hit"), t.Sounds.sfx.play("hit")
        }, i.prototype.updateAnim = function(e, i, s) {
            s || (e ? "run" !== this._spriterGroup.currentAnimationName && (this._spriterGroup.playAnimationByName("run"), t.Sounds.sfx.play("land")) : i > 0 && "fall" !== this._spriterGroup.currentAnimationName && this._spriterGroup.playAnimationByName("fall")), this._spriterGroup.updateAnimation()
        }, i
    }(Phaser.Sprite);
    t.Player = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(t) {
        function e(e, i, s) {
            t.call(this, e, i);
            var n = 0 * s,
                r = new Phaser.Sprite(e, n, 0, "Sprites", "Record");
            r.anchor.x = .5, this.add(r);
        }
        return __extends(e, t), e
    }(Phaser.Group);
    t.Record = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(t) {
        function e(e, i) {
            t.call(this, e, i), this._icon = new Phaser.Sprite(e, 0, 0, "Sprites", "GoldUI"), this._icon.anchor.set(.5, .5), this.add(this._icon), this._text = new Phaser.BitmapText(e, this._icon.width / 2 * 1.2, 0, "Font", "0", 40, "left"), this._text.anchor.y = .5, this.add(this._text), this._tween = e.add.tween(this._icon.scale).to({
                x: 1.2,
                y: 1.2
            }, 1, function(t) {
                return Math.sin(Math.PI * t)
            }, !1, 0)
        }
        return __extends(e, t), Object.defineProperty(e.prototype, "score", {
            set: function(t) {
                this._text.text = "" + t
            },
            enumerable: !0,
            configurable: !0
        }), e.prototype.bounce = function() {
            this._tween.isRunning || this._tween.start()
        }, e
    }(Phaser.Group);
    t.ScoreUI = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(t) {
        function e(e, i, s) {
            t.call(this, e, 0, 0, "Sprites", "Shadow"), this._playerPosition = i, this._walls = s, this.anchor.set(.5, .5), this.visible = !1
        }
        return __extends(e, t), e.prototype.postUpdate = function() {
            for (var t = this._playerPosition.x - 20, e = Number.MAX_VALUE, i = this._playerPosition.x + 20, s = Number.MAX_VALUE, n = 0; n < this._walls.length; n++) {
                var r = this._walls.getChildAt(n);
                r.x <= t && r.x + 64 > t && (e = Math.min(e, r.y)), r.x <= i && r.x + 64 > i && (s = Math.min(s, r.y))
            }
            if (e < Number.MAX_VALUE && e === s && e > this._playerPosition.y) {
                var a = 1 / (1 + (e - this._playerPosition.y) / 500);
                this.scale.x = a, this.position.set(this._playerPosition.x, e), this.visible = !0
            } else this.visible = !1
        }, e
    }(Phaser.Sprite);
    t.Shadow = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var Generator;
! function(t) {
    var e = function() {
        function e(e) {
            this._rnd = e, this._platformLengthDecrease = t.Parameters.PLATFORM_LENGTH_DECREASER_MIN, this._jumpLengthDecrease = t.Parameters.JUMP_LENGTH_DECREASER_MIN, this._spikesProbability = t.Parameters.SPIKES_PROB_MIN, this._bonusJumpProbability = t.Parameters.BONUS_JUMP_PROB_MIN, this._bonusJumpCount = t.Parameters.BONUS_JUMP_COUNT_MIN
        }
        return Object.defineProperty(e.prototype, "platformLengthDecrease", {
            get: function() {
                return this._platformLengthDecrease
            },
            enumerable: !0,
            configurable: !0
        }), Object.defineProperty(e.prototype, "jumpLengthDecrease", {
            get: function() {
                return this._jumpLengthDecrease
            },
            enumerable: !0,
            configurable: !0
        }), Object.defineProperty(e.prototype, "spikesProbability", {
            get: function() {
                return this._spikesProbability
            },
            enumerable: !0,
            configurable: !0
        }), Object.defineProperty(e.prototype, "bonusJumpProbability", {
            get: function() {
                return this._bonusJumpProbability
            },
            enumerable: !0,
            configurable: !0
        }), Object.defineProperty(e.prototype, "bonusJumpCount", {
            get: function() {
                return this._bonusJumpCount
            },
            enumerable: !0,
            configurable: !0
        }), e.prototype.mapLinear = function(t, e, i, s, n) {
            return t = Phaser.Math.clamp(t, e, i), Phaser.Math.mapLinear(t, e, i, s, n)
        }, e.prototype.update = function(e) {
            this._platformLengthDecrease = Math.round(this.mapLinear(e, t.Parameters.PLATFORM_LENGTH_DECREASER_START_TILE, t.Parameters.PLATFORM_LENGTH_DECREASER_END_TILE, t.Parameters.PLATFORM_LENGTH_DECREASER_MIN, t.Parameters.PLATFORM_LENGTH_DECREASER_MAX)), this._jumpLengthDecrease = Math.round(this.mapLinear(e, t.Parameters.JUMP_LENGTH_DECREASER_START_TILE, t.Parameters.JUMP_LENGTH_DECREASER_END_TILE, t.Parameters.JUMP_LENGTH_DECREASER_MIN, t.Parameters.JUMP_LENGTH_DECREASER_MAX)), this._spikesProbability = Math.round(this.mapLinear(e, t.Parameters.SPIKES_PROB_START_TILE, t.Parameters.SPIKES_PROB_END_TILE, t.Parameters.SPIKES_PROB_MIN, t.Parameters.SPIKES_PROB_MAX)), this._bonusJumpProbability = Math.round(this.mapLinear(e, t.Parameters.BONUS_JUMP_START_TILE, t.Parameters.BONUS_JUMP_END_TILE, t.Parameters.BONUS_JUMP_PROB_MIN, t.Parameters.BONUS_JUMP_PROB_MAX)), this._bonusJumpCount = Math.round(this.mapLinear(e, t.Parameters.BONUS_JUMP_COUNT_START_TILE, t.Parameters.BONUS_JUMP_COUNT_END_TILE, t.Parameters.BONUS_JUMP_COUNT_MIN, t.Parameters.BONUS_JUMP_COUNT_MAX))
        }, e.prototype.toString = function() {
            return "platformLengthDecrease: " + this._platformLengthDecrease + ", jumpLengthDecrease: " + this._jumpLengthDecrease + ", spikesProbabilty: " + this._spikesProbability + ", bonusJumpProbability: " + this._bonusJumpProbability + ", bonusJumpCount: " + this._bonusJumpCount
        }, e
    }();
    t.Difficulty = e
}(Generator || (Generator = {}));
var Generator;
! function(t) {
    var e = -1e4,
        i = function() {
            function i(e) {
                this.onRandomPlatform = new Phaser.Signal, this.onPatternPlatform = new Phaser.Signal, this.onBonusJump = new Phaser.Signal, this._lastGeneratedPiece = null, this._piecesQueue = [], this._piecesQueueTop = 0, this._hlpPoint = new Phaser.Point, this._rnd = e, this._jumpTables = t.JumpTables.instance, this._piecesPool = new Helper.Pool(t.Piece, 16)
            }
            return i.prototype.createPiece = function() {
                var t = this._piecesPool.createItem();
                return t
            }, i.prototype.destroyPiece = function(t) {
                this._piecesPool.destroyItem(t)
            }, Object.defineProperty(i.prototype, "hasPieces", {
                get: function() {
                    return this._piecesQueueTop > 0
                },
                enumerable: !0,
                configurable: !0
            }), i.prototype.addPieceIntoQueue = function(t) {
                this._piecesQueue[this._piecesQueueTop++] = t
            }, i.prototype.getPieceFromQueue = function() {
                if (0 === this._piecesQueueTop) return null;
                for (var t = this._piecesQueue[0], e = 0; e < this._piecesQueueTop - 1; e++) this._piecesQueue[e] = this._piecesQueue[e + 1];
                return this._piecesQueue[--this._piecesQueueTop] = null, t
            }, i.prototype.setPiece = function(t, e, i, s, n) {
                void 0 === s && (s = 0), void 0 === n && (n = 0);
                var r = this.createPiece();
                return r.position.set(t, e), r.offset.set(s, n), r.length = i, this.addPieceIntoQueue(r), r
            }, i.prototype.generate = function(i, s, n, r, a, o) {
                var h = this.createPiece(),
                    u = t.Parameters.UBOUND,
                    l = t.Parameters.LBOUND,
                    _ = this._jumpTables.maxOffsetY(),
                    c = -_,
                    p = i.y - u,
                    m = a;
                m === e && (m = this._rnd.integerInRange(0, l - u), m -= p, m = Phaser.Math.clamp(m, _, c));
                var d = Phaser.Math.clamp(p + m, 0, l - u);
                h.position.y = d + u, h.offset.y = h.position.y - i.y;
                var f = r;
                if (f === e || a !== e && a !== h.offset.y) {
                    var y = this._jumpTables.minOffsetX(h.offset.y),
                        P = this._jumpTables.maxOffsetX(h.offset.y);
                    (o || null !== this._lastGeneratedPiece && this._lastGeneratedPiece.bonusJump) && (y = Math.min(Math.max(y, 2), P)), o || (P = Math.max(y, P + s.jumpLengthDecrease)), f = this._rnd.integerInRange(y, P)
                }
                if (h.position.x = i.x + f, h.offset.x = f, n !== e ? h.length = n : h.length = this._rnd.integerInRange(t.Parameters.PLATFORM_LENGTH_MIN, t.Parameters.PLATFORM_LENGTH_MAX + s.platformLengthDecrease), null !== this._lastGeneratedPiece && 0 === this._lastGeneratedPiece.spikesPattern && !o && this._rnd.integerInRange(0, 99) < s.spikesProbability) {
                    h.length = this._rnd.integerInRange(5, 9);
                    var g = t.Parameters.SPIKE_PATTERNS[h.length];
                    h.spikesPattern = g[this._rnd.integerInRange(0, g.length - 1)]
                } else h.spikesPattern = 0;
                return h.bonusJump = o, this._lastGeneratedPiece = h, h
            }, i.prototype.generatePieces = function(e, i) {
                var s = this._rnd.integerInRange(0, 99);
                s < i.bonusJumpProbability && null !== this._lastGeneratedPiece && !this._lastGeneratedPiece.bonusJump ? this.generateBonusJump(e, i) : (s = this._rnd.integerInRange(0, 99), s < t.Parameters.GENERATE_RANDOM ? this.generateRandomly(e, i) : this.generatePattern(e, i))
            }, i.prototype.generateRandomly = function(t, i) {
                var s = this._lastGeneratedPiece,
                    n = this.generate(t, i, e, e, e, !1);
                this.addPieceIntoQueue(n), this.onRandomPlatform.dispatch(n, s)
            }, i.prototype.generatePattern = function(i, s) {
                var n = this._piecesQueueTop,
                    r = this._hlpPoint;
                r.copyFrom(i);
                var a = e;
                this._rnd.integerInRange(0, 99) < t.Parameters.KEEP_LENGTH_IN_PATTERN && (a = this._rnd.integerInRange(t.Parameters.PLATFORM_LENGTH_MIN, t.Parameters.PLATFORM_LENGTH_MAX + s.platformLengthDecrease));
                for (var o = 2, h = 0; o > h; h++) {
                    var u = this._lastGeneratedPiece,
                        l = this.generate(r, s, a, e, e, !1);
                    r.copyFrom(l.position), r.x += l.length - 1, this.addPieceIntoQueue(l), this.onPatternPlatform.dispatch(l, u, h, 0, null)
                }
                for (var _ = 1, h = 0; _ > h; h++)
                    for (var c = 0; o > c; c++) {
                        var u = this._lastGeneratedPiece,
                            p = this._piecesQueue[n + c],
                            l = this.generate(r, s, a, p.offset.x, p.offset.y, !1);
                        r.copyFrom(l.position), r.x += l.length - 1, this.addPieceIntoQueue(l), this.onPatternPlatform.dispatch(l, u, c, h + 1, p)
                    }
            }, i.prototype.generateBonusJump = function(i, s) {
                for (var n, r = this._rnd.integerInRange(t.Parameters.BONUS_JUMP_COUNT_MIN, s.bonusJumpCount), a = this._lastGeneratedPiece, o = 0; r > o; o++) n = 0 === o ? this.generate(i, s, 1, e, e, !0) : this.generate(a.position, s, 1, a.offset.x, a.offset.y, !0), this.addPieceIntoQueue(n), this.onBonusJump.dispatch(n, a, o), a = n
            }, i
        }();
    t.Generator = i
}(Generator || (Generator = {}));
var Generator;
! function(t) {
    var e = function() {
        function t() {
            this.offsetY = 0, this.offsetX = 0
        }
        return t.prototype.toString = function() {
            return "offsetX: " + this.offsetX + ", offsetY: " + this.offsetY
        }, t
    }();
    t.Jump = e
}(Generator || (Generator = {}));
var Generator;
! function(t) {
    var e = function() {
        function e() {
            this._jumpVelocities = [], this._jumpDefs = [], this._jumpOffsetsY = [], this._jumpOffsetYMax = 0, this._jumpOffsetXMins = {}, this._jumpOffsetXMaxs = {}, e._DEBUG && this.createDebugBitmap(), this.calculateJumpVelocities(), this.calculateJumpTables()
        }
        return Object.defineProperty(e, "instance", {
            get: function() {
                return null === e._instance && (e._instance = new e), e._instance
            },
            enumerable: !0,
            configurable: !0
        }), e.prototype.calculateJumpVelocities = function() {
            for (var e = 0; e <= t.Parameters.HEIGHT_STEPS; e++) {
                var i = t.Parameters.HEIGHT_MIN + (t.Parameters.HEIGHT_MAX - t.Parameters.HEIGHT_MIN) / t.Parameters.HEIGHT_STEPS * e;
                this._jumpVelocities[e] = -Math.sqrt(2 * i * t.Parameters.GRAVITY)
            }
        }, Object.defineProperty(e.prototype, "minJumpVelocity", {
            get: function() {
                return this._jumpVelocities[0]
            },
            enumerable: !0,
            configurable: !0
        }), Object.defineProperty(e.prototype, "maxJumpVelocity", {
            get: function() {
                return this._jumpVelocities[this._jumpVelocities.length - 1]
            },
            enumerable: !0,
            configurable: !0
        }), e.prototype.calculateJumpTables = function() {
            for (var e = 0; e <= t.Parameters.HEIGHT_STEPS; e++) {
                this._jumpDefs[e] = [];
                for (var i = 0; 1 > i; i++) this.calculateJumpCurve(i, e)
            }
            this.analyzeJumpTables()
        }, e.prototype.calculateJumpCurve = function(i, s) {
            for (var n, r, a = 1 / 60, o = this._jumpVelocities[s], h = i * t.Parameters.CELL_SIZE / t.Parameters.CELL_STEPS + t.Parameters.CELL_SIZE / t.Parameters.CELL_STEPS / 2, u = 0, l = 0, _ = [], c = {}, p = t.Parameters.PLAYER_BODY_WIDTH / 2 * .5, m = e._DEBUG ? e.debugBitmapData : null, d = 4 * t.Parameters.CELL_SIZE; l < t.Parameters.GRID_HEIGHT;) {
                n = h, r = u, o += t.Parameters.GRAVITY * a, u += o * a, h += t.Parameters.VELOCITY_X * a, e._DEBUG && m.rect(h, u + d, 2, 2, "#FFFFFF");
                var f = void 0,
                    y = void 0;
                if (l = Math.floor(u / t.Parameters.CELL_SIZE), o > 0 && l > Math.floor(r / t.Parameters.CELL_SIZE)) {
                    var P = Math.floor(u / t.Parameters.CELL_SIZE) * t.Parameters.CELL_SIZE,
                        g = n + (h - n) * (P - r) / (u - r);
                    f = Math.floor((g - p) / t.Parameters.CELL_SIZE), y = Math.floor((g + p) / t.Parameters.CELL_SIZE);
                    for (var b = f; y >= b; b++) {
                        var E = b + (l << 8);
                        if ("undefined" == typeof c[E]) {
                            var S = new t.Jump;
                            S.offsetX = b, S.offsetY = l, _.push(S)
                        }
                    }
                    if (e._DEBUG) {
                        var G = P + d,
                            T = "#4040FF",
                            O = g - t.Parameters.PLAYER_BODY_WIDTH / 2,
                            v = g + t.Parameters.PLAYER_BODY_WIDTH / 2;
                        m.line(O, G, v, G, T), T = "#0000FF", O = g - p, v = g + p, m.line(O, G, v, G, T), m.line(O, G - 3, O, G + 3, T), m.line(v, G - 3, v, G + 3, T)
                    }
                }
                f = Math.floor((h - p) / t.Parameters.CELL_SIZE), y = Math.floor((h + p) / t.Parameters.CELL_SIZE);
                for (var b = f; y >= b; b++) {
                    var E = b + (l << 8);
                    "undefined" == typeof c[E] && (c[E] = E)
                }
            }
            this._jumpDefs[s][i] = _
        }, e.prototype.analyzeJumpTables = function() {
            this._jumpOffsetYMax = 0;
            for (var t = 0; t < this._jumpDefs.length; t++) this._jumpOffsetsY[t] = this._jumpDefs[t][0][0].offsetY, this._jumpOffsetYMax = Math.min(this._jumpOffsetYMax, this._jumpOffsetsY[t]);
            for (var t = 1; t < this._jumpDefs.length; t++) {
                for (var e = this._jumpDefs[t][0], i = 0; i < e.length; i++) {
                    var s = e[i],
                        n = this._jumpOffsetXMins[s.offsetY];
                    this._jumpOffsetXMins[s.offsetY] = "undefined" != typeof n ? Math.min(n, s.offsetX) : s.offsetX
                }
                e = this._jumpDefs[t][this._jumpDefs[t].length - 1];
                for (var i = 0; i < e.length; i++) {
                    var s = e[i],
                        r = this._jumpOffsetXMaxs[s.offsetY];
                    this._jumpOffsetXMaxs[s.offsetY] = "undefined" != typeof r ? Math.max(r, s.offsetX) : s.offsetX
                }
            }
        }, e.prototype.maxOffsetY = function(t) {
            return void 0 === t && (t = -1), -1 === t ? this._jumpOffsetYMax : this._jumpOffsetsY[t]
        }, e.prototype.maxOffsetX = function(t) {
            var e = this._jumpOffsetXMaxs[t];
            return "undefined" == typeof e && (e = 0), e
        }, e.prototype.minOffsetX = function(t) {
            var e = this._jumpOffsetXMins[t];
            return "undefined" == typeof e && (e = 0), e
        }, e.setDebug = function(t, i) {
            e._DEBUG = t, e._globals = i, !t || "undefined" != typeof i && null !== i || (e._DEBUG = !1)
        }, Object.defineProperty(e, "debugBitmapData", {
            get: function() {
                return e._debugBmd
            },
            enumerable: !0,
            configurable: !0
        }), e.prototype.createDebugBitmap = function() {
            var i = e._globals,
                s = new Phaser.BitmapData(i.game, "Grid", i.GAME_WIDTH, i.GAME_HEIGHT);
            s.fill(192, 192, 192);
            for (var n = 0; n < i.GAME_HEIGHT; n += t.Parameters.CELL_SIZE) s.line(0, n + .5, i.GAME_WIDTH - 1, n + .5);
            for (var n = 0; n < i.GAME_WIDTH; n += t.Parameters.CELL_SIZE) s.line(n + .5, 0, n + .5, i.GAME_HEIGHT - 1), s.text("" + n / t.Parameters.CELL_SIZE, n + 20, 20, "24px Courier", "#FFFF00");
            e._debugBmd = s
        }, e._instance = null, e._DEBUG = !1, e
    }();
    t.JumpTables = e
}(Generator || (Generator = {}));
var Generator;
! function(t) {
    var e = function() {
        function t() {}
        return t.GRID_HEIGHT = 10, t.CELL_SIZE = 64, t.CELL_STEPS = 4, t.GRAVITY = 2400, t.PLAYER_BODY_WIDTH = 30, t.PLAYER_BODY_HEIGHT = 90, t.HEIGHT_MIN = .75 * t.CELL_SIZE, t.HEIGHT_MAX = 2.9 * t.CELL_SIZE, t.HEIGHT_STEPS = 4, t.VELOCITY_X = 300, t.UBOUND = 2, t.LBOUND = 8, t.GENERATE_RANDOM = 50, t.KEEP_LENGTH_IN_PATTERN = 75, t.PLATFORM_LENGTH_MIN = 2, t.PLATFORM_LENGTH_MAX = 5, t.PLATFORM_LENGTH_DECREASER_MIN = 0, t.PLATFORM_LENGTH_DECREASER_MAX = -2, t.PLATFORM_LENGTH_DECREASER_START_TILE = 100, t.PLATFORM_LENGTH_DECREASER_END_TILE = 200, t.JUMP_LENGTH_DECREASER_MIN = -1, t.JUMP_LENGTH_DECREASER_MAX = 0, t.JUMP_LENGTH_DECREASER_START_TILE = 0, t.JUMP_LENGTH_DECREASER_END_TILE = 50, t.SPIKES_PROB_MIN = 0, t.SPIKES_PROB_MAX = 25, t.SPIKES_PROB_START_TILE = 30, t.SPIKES_PROB_END_TILE = 80, t.BONUS_JUMP_PROB_MIN = 0, t.BONUS_JUMP_PROB_MAX = 30, t.BONUS_JUMP_START_TILE = 50, t.BONUS_JUMP_END_TILE = 200, t.BONUS_JUMP_COUNT_MIN = 1, t.BONUS_JUMP_COUNT_MAX = 3, t.BONUS_JUMP_COUNT_START_TILE = 50, t.BONUS_JUMP_COUNT_END_TILE = 300, t.SPIKE_PATTERNS = [
            [],
            [],
            [],
            [],
            [],
            [4],
            [12],
            [28, 20],
            [24, 36],
            [56, 108, 68]
        ], t
    }();
    t.Parameters = e
}(Generator || (Generator = {}));
var Generator;
! function(t) {
    var e = function() {
        function t() {
            this.position = new Phaser.Point(0, 0), this.offset = new Phaser.Point(0, 0)
        }
        return t
    }();
    t.Piece = e
}(Generator || (Generator = {}));
var Helper;
! function(t) {
    var e = function() {
        function t(t, e, i) {
            void 0 === i && (i = null), this._newFunction = null, this._count = 0, this._pool = [], this._canGrow = !0, this._poolSize = 0, this._classType = t, this._newFunction = i;
            for (var s = 0; e > s; s++) {
                var n = this.newItem();
                this._pool[this._count++] = n
            }
        }
        return t.prototype.createItem = function() {
            return 0 === this._count ? this._canGrow ? this.newItem() : null : this._pool[--this._count]
        }, t.prototype.destroyItem = function(t) {
            this._pool[this._count++] = t
        }, t.prototype.newItem = function() {
            return ++this._poolSize, null !== this._newFunction ? this._newFunction() : new this._classType
        }, Object.defineProperty(t.prototype, "newFunction", {
            set: function(t) {
                this._newFunction = t
            },
            enumerable: !0,
            configurable: !0
        }), Object.defineProperty(t.prototype, "canGrow", {
            set: function(t) {
                this._canGrow = t
            },
            enumerable: !0,
            configurable: !0
        }), t
    }();
    t.Pool = e
}(Helper || (Helper = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function() {
        function t() {
            this.record = 0, this.sound = !0
        }
        return Object.defineProperty(t, "instance", {
            get: function() {
                return null === t._instance && (t._instance = new t), t._instance
            },
            enumerable: !0,
            configurable: !0
        }), t.prototype.load = function() {
            if (this.localStorageSupported()) {
                var t = localStorage.getItem("JumperBoyAdventures_save");
                if (null === t || void 0 === t) return;
                var e = JSON.parse(t);
                this.record = e.record, this.sound = e.sound
            }
        }, t.prototype.save = function() {
            if (this.localStorageSupported()) {
                var t = JSON.stringify(this);
                localStorage.setItem("JumperBoyAdventures_save", t)
            }
        }, t.prototype.localStorageSupported = function() {
            try {
                return "localStorage" in window && null !== window.localStorage
            } catch (t) {
                return !1
            }
        }, t._instance = null, t
    }();
    t.Preferences = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function() {
        function t() {}
        return t.AUDIO_JSON = {
            resources: ["../assets\\Sfx.ogg", "../assets\\Sfx.m4a"],
            spritemap: {
                end: {
                    start: 0,
                    end: 0.025736961451247,
                    loop: !1
                },
                bonus_jump: {
                    start: 5,
                    end: 5.684943310657596,
                    loop: !1
                },
                gold: {
                    start: 3.225873015873016,
                    end: 3.995873015873016,
                    loop: !1
                },
                hit: {
                    start: 0,
                    end: 0.09859410430839,
                    loop: !1
                },
                jump: {
                    start: 5,
                    end: 5.684943310657596,
                    loop: !1
                },
                land: {
                    start: 11,
                    end: 11.123083900226757,
                    loop: !1
                },
                mud_fall: {
                    start: 5.705873015873016,
                    end: 8.882630385487528,
                    loop: !1
                },
                select: {
                    start: 9,
                    end: 10.052879818594104,
                    loop: !1
                }
            }
        }, t
    }();
    t.Sounds = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(e) {
        function i() {
            e.apply(this, arguments), this._userScale = new Phaser.Point(1, 1), this._gameDims = new Phaser.Point
        }
        return __extends(i, e), i.prototype.init = function() {
            this.calcGameDims(), this.scale.scaleMode = Phaser.ScaleManager.USER_SCALE, this.scale.setUserScale(this._userScale.x, this._userScale.y), this.scale.pageAlignHorizontally = !0, this.scale.pageAlignVertically = !0, this.scale.setResizeCallback(this.gameResized, this), this.game.device.desktop || (this.scale.forceOrientation(!0, !1), this.scale.onOrientationChange.add(this.orientationChange, this))
        }, i.prototype.preload = function() {
            this.load.bitmapFont("Font", "../assets/Font.png", "../assets/Font.xml")
        }, i.prototype.create = function() {
            this.game.state.start("Preload")
        }, i.prototype.calcGameDims = function() {
            var e = window.innerWidth,
                i = window.innerHeight,
                s = (i + .01) / t.Global.GAME_HEIGHT,
                n = Math.round(e / (i / t.Global.GAME_HEIGHT)),
                r = (e + .01) / n;
            this._userScale.set(s, r), this._gameDims.set(n, t.Global.GAME_HEIGHT)
        }, i.prototype.gameResized = function(t, e) {
            if (!t.incorrectOrientation) {
                var i = this._userScale.x,
                    s = this._userScale.y;
                this.calcGameDims();
                var n = this._gameDims,
                    r = this._userScale;
                if (n.x !== this.game.width || n.y !== this.game.height || Math.abs(r.x - i) > .001 || Math.abs(r.y - s) > .001) {
                    this.scale.setGameSize(n.x, n.y), this.scale.setUserScale(r.x, r.y);
                    var a = this.game.state.getCurrentState();
                    "function" == typeof a.onResize && a.onResize(n.x, n.y)
                }
            }
        }, i.prototype.orientationChange = function(t, e, i) {
            t.isLandscape ? this.leaveIncorrectOrientation() : this.enterIncorrectOrientation()
        }, i.prototype.enterIncorrectOrientation = function() {
            document.getElementById("orientation").style.display = "block";
            var t = this.game.state.getCurrentState();
            "function" == typeof t.onPause && t.onPause()
        }, i.prototype.leaveIncorrectOrientation = function() {
            document.getElementById("orientation").style.display = "none";
            var t = this.game.state.getCurrentState();
            "function" == typeof t.onResume && t.onResume()
        }, i
    }(Phaser.State);
    t.Boot = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(e) {
        function i() {
            e.apply(this, arguments)
        }
        return __extends(i, e), i.prototype.create = function() {
            this.stage.backgroundColor = 10541679, this.setView(this.game.width, this.game.height);
            var e = this.cache.getImage("TreesBg").height;
            this._treesBg = this.add.tileSprite(0, -this.game.height / 2, this.game.width, e, "TreesBg"), this._treesBg.anchor.x = .5, this._ground = this.game.add.sprite(0, this.game.height / 2, this.generateGround()), this._ground.anchor.set(.5, 1), this.createJumperBoy(), this.createTitle(), this.createStartButton(), this.sound.mute = !t.Preferences.instance.sound, this.createSoundButton(), t.Sounds.musicMenu.play()
        }, i.prototype.createJumperBoy = function() {
            this._runDirection = this.rnd.sign();
            var t = new Spriter.Loader,
                e = new Spriter.SpriterXml(this.cache.getXML("JumperBoyAnim")),
                s = t.load(e);
            this._JumperBoy = new Spriter.SpriterGroup(this.game, s, "Sprites", "JumperBoy", "run", 100), this._JumperBoy.scale.x = this._runDirection, this._JumperBoy.position.set((this.game.width / 2 + 200) * this._runDirection, i.JumperBoy_Y), this.world.add(this._JumperBoy)
        }, i.prototype.createTitle = function() {
            var t = this.add.sprite(0, -40, "Sprites", "Logo");
            t.anchor.set(.5, .5), this.add.tween(t).to({
                angle: 3
            }, 2500, function(t) {
                return Math.sin(2 * t * Math.PI)
            }, !0, 0, -1), this.add.tween(t.scale).to({
                x: 1.02,
                y: 1.02
            }, 1250, function(t) {
                return Math.sin(2 * t * Math.PI)
            }, !0, 0, -1)
        }, i.prototype.createStartButton = function() {
            var e = this.add.button(0, 220, "Sprites", function() {
                this.game.state.start("Play")
            }, this, "Start", "Start", "Start", "Start");
            e.anchor.set(.5, .5), e.onInputDown.add(function() {
                e.scale.set(.9, .9), t.Sounds.sfx.play("select")
            }, this), this.add.tween(e.scale).to({
                x: 1.2,
                y: .9
            }, 750, function(t) {
                var e = 3 * t,
                    i = 2 * -t;
                return Math.sin(e * Math.PI * 2) * Math.exp(i)
            }, !0, 2e3, -1).repeatDelay(2e3)
        }, i.prototype.createSoundButton = function() {
            var e = t.Preferences.instance.sound ? "Sound_on" : "Sound_off",
                s = this.add.button(this.game.width / 2 - i.SOUND_BUTTON_OFFSET, -this.game.height / 2 + i.SOUND_BUTTON_OFFSET, "Sprites", function() {
                    var e = t.Preferences.instance;
                    e.sound = !e.sound, this.sound.mute = !e.sound;
                    var i = e.sound ? "Sound_on" : "Sound_off";
                    s.setFrames(i, i, i, i), t.Sounds.sfx.play("select"), e.save()
                }, this, e, e, e, e);
            s.anchor.set(.5, .5), this._sound = s
        }, i.prototype.onResize = function(t, e) {
            this.setView(t, e), this._ground.setTexture(this.generateGround()), this._treesBg.width = t, this._sound.position.set(this.game.width / 2 - i.SOUND_BUTTON_OFFSET, -this.game.height / 2 + i.SOUND_BUTTON_OFFSET)
        }, i.prototype.setView = function(t, e) {
            this.world.setBounds(-t / 2, -e / 2, t / 2, e / 2), this.camera.focusOnXY(0, 0)
        }, i.prototype.generateGround = function() {
            var t = new Phaser.Graphics(this.game);
            t.beginFill(2836800), t.moveTo(0, 0), t.quadraticCurveTo(this.game.width / 0, i.GROUND_CP_Y, this.game.width, 0), t.lineTo(this.game.width, i.GROUND_HEIGHT), t.lineTo(0, i.GROUND_HEIGHT), t.endFill();
            var e = t.generateTexture();
            return t.destroy(), e
        }, i.prototype.update = function() {
            var t = this.time.elapsed / 1e3;
            this._treesBg.tilePosition.x -= i.BG_SPEED_X * t, this._JumperBoy.updateAnimation(), this._JumperBoy.x += i.JumperBoy_SPEED * t * this._runDirection, this._JumperBoy.x * this._runDirection > this.game.width / 2 + 200 && (this._runDirection *= -1, this._JumperBoy.x = -(this.game.width / 2 + 200) * this._runDirection, this._JumperBoy.scale.x = this._runDirection);
            var e = 0,
                s = i.GROUND_CP_Y,
                n = 0,
                r = Phaser.Math.clamp(Phaser.Math.mapLinear(this._JumperBoy.x, -this.game.width / 2, this.game.width / 2, 0, 1), 0, 1);
            this._JumperBoy.y = i.JumperBoy_Y + Phaser.Math.linear(Phaser.Math.linear(e, s, r), Phaser.Math.linear(s, n, r), r)
        }, i.prototype.shutdown = function() {
            t.Sounds.musicMenu.stop()
        }, i.GROUND_HEIGHT = 0, i.GROUND_CP_Y = 0, i.JumperBoy_Y = i.GROUND_CP_Y - 0, i.JumperBoy_SPEED = 0, i.BG_SPEED_X = 0, i.SOUND_BUTTON_OFFSET = 50, i
    }(Phaser.State);
    t.Menu = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(e) {
        function i() {
            e.apply(this, arguments), this._ready = !1
        }
        return __extends(i, e), i.prototype.preload = function() {
            this.stage.backgroundColor = 0, this.setView(this.game.width, this.game.height);
            var e = new Phaser.Graphics(this.game);
            e.beginFill(15790320), e.drawRect(0, 0, 8, 8), this._loadingBar = this.add.sprite(-i.LOADING_BAR_WIDTH / 2, 0, e.generateTexture()), this._loadingBar.width = 0, this._loadingBar.height = 48, e.destroy(), this._loadingText = this.add.bitmapText(0, 0, "Font", "0%", 40), this._loadingText.anchor.x = .5, this.load.atlas("Sprites", "../assets/Sprites.png", "../assets/Sprites.json"), this.load.xml("JumperBoyAnim", "../assets/JumperBoy.xml"), this.load.image("Mud", "../assets/Mud.png"), this.load.image("Hill", "../assets/Hill.png"), this.load.image("TreesBg", "../assets/TreesBg.png"), this.load.audiosprite("Sfx", t.Sounds.AUDIO_JSON.resources, null, t.Sounds.AUDIO_JSON), this.load.audio("MusicGame", ["../assets/MusicGame.ogg", "../assets/MusicGame.m4a"]), this.load.audio("MusicMenu", ["../assets/MusicMenu.ogg", "../assets/MusicMenu.m4a"])
        }, i.prototype.onResize = function(t, e) {
            this.setView(t, e)
        }, i.prototype.setView = function(t, e) {
            this.world.setBounds(-t / 2, -e / 2, t / 2, e / 2), this.camera.focusOnXY(0, 0)
        }, i.prototype.loadUpdate = function() {
            this._loadingBar.width = i.LOADING_BAR_WIDTH * this.load.progress / 100, this._loadingText.text = this.load.progress + "%"
        }, i.prototype.create = function() {
            t.Sounds.sfx = this.add.audioSprite("Sfx"), t.Sounds.musicGame = this.add.audio("MusicGame"), t.Sounds.musicGame.loop = !0, t.Sounds.musicMenu = this.add.audio("MusicMenu"), t.Sounds.musicMenu.loop = !0
        }, i.prototype.update = function() {
            this._ready === !1 && this.cache.isSoundDecoded("Sfx") && this.cache.isSoundDecoded("MusicGame") && this.cache.isSoundDecoded("MusicMenu") && (this._ready = !0, this.game.state.start("Menu"))
        }, i.LOADING_BAR_WIDTH = 300, i
    }(Phaser.State);
    t.Preload = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));
var JumperBoyAdventures;
! function(t) {
    var e = function(e) {
        function i() {
            e.apply(this, arguments), this._jumpTimer = 0, this._bonusJump = !1, this._gameOver = !1, this._justDown = !1, this._justUp = !1, this._score = 0, this._touchingDown = !1, this._runCounter = 0
        }
        return __extends(i, e), i.prototype.render = function() {}, i.prototype.create = function() {
            this.stage.backgroundColor = 10541679, this.camera.bounds = null, this.physics.arcade.gravity.y = Generator.Parameters.GRAVITY, Generator.JumpTables.instance, this._bg = new t.Background(this.game, this.world), this._mainLayer = new t.MainLayer(this.game, this.world), this._player = new t.Player(this.game), this._player.position.set(96, 64), this.world.add(this._player), this._shadow = new t.Shadow(this.game, this._player.position, this._mainLayer.walls);
            var e = this._mainLayer.getChildIndex(this._mainLayer.walls);
            this._mainLayer.addChildAt(this._shadow, e + 1);
            var i = new Phaser.Particles.Arcade.Emitter(this.game, 0, 0, 16);
            i.makeParticles("Sprites", ["DustParticle"]), i.setYSpeed(-50, -20), i.setRotation(0, 0), i.setAlpha(1, 0, 500, Phaser.Easing.Linear.None), i.gravity = -Generator.Parameters.GRAVITY, this.world.add(i), this._dustEmitter = i, this._scoreUI = new t.ScoreUI(this.game, this.world), this._scoreUI.fixedToCamera = !0, this._scoreUI.cameraOffset.set(45, 30), this._jumpKey = this.game.input.keyboard.addKey(Phaser.KeyCode.SPACEBAR), this.game.input.onDown.add(function() {
                this._justDown = !0
            }, this), this.game.input.onUp.add(function() {
                this._justUp = !0
            }, this), this._gameOver = !1, this._score = 0, this._bonusJump = !1, this._justDown = this._justUp = !1, t.Sounds.musicGame.play()
        }, i.prototype.update = function() {
            this._gameOver || (this.updatePhysics(), this.camera.x = this._player.x - 256, this._mainLayer.generate(this.camera.x / Generator.Parameters.CELL_SIZE)), this._player.y > this.game.height - 104 && (this._player.y = this.game.height - 104, this.gameOver(), t.Sounds.musicGame.stop(), this._player.animateDeath(), integration.showBanner(), integration.showInterstitial(), integration.getLastScore(this._score));
            var e = this._player.body;
            this._player.updateAnim(e.velocity.y >= 0 && e.touching.down, e.velocity.y, this._gameOver), this._touchingDown || !e.touching.down || this._gameOver || this.emittDustLanding(), this._touchingDown = e.touching.down, this.emittDustRunning(), this._bg.updateLayers(this.camera.x)
        }, i.prototype.updatePhysics = function() {
            var t = this._player.body;
            if (this.physics.arcade.overlap(this._player, this._mainLayer.items, this.onOverlap, null, this), !this._gameOver) {
                t.touching.none = !0, t.touching.up = t.touching.down = t.touching.left = t.touching.right = !1;
                var e = this.physics.arcade.collide(this._player, this._mainLayer.walls);
                if (e && t.touching.right) return t.velocity.set(0, 0), this.gameOver(), void this._player.animateHit();
                t.velocity.x = Generator.Parameters.VELOCITY_X, this._jumpKey.justDown && (this._justDown = !0), this._jumpKey.justUp && (this._justUp = !0);
                var i = Generator.JumpTables.instance;
                (this._justDown && t.touching.down && this.game.time.now > this._jumpTimer || this._justDown && this._bonusJump) && (t.velocity.y = i.maxJumpVelocity, this._jumpTimer = this.game.time.now + 150, this._justDown = !1, this._bonusJump = !1, this._player.animateJump()), this._justUp && t.velocity.y < i.minJumpVelocity && (t.velocity.y = i.minJumpVelocity), t.velocity.y <= 0 && (this._justDown = !1), this._justUp && (this._justDown = !1), this._justUp = !1
            }
        }, i.prototype.onOverlap = function(e, i) {
            0 === i.itemType ? (this._player.body.velocity.set(0, 0), this._player.animateHit(), this.gameOver()) : 1 === i.itemType ? (this._bonusJump = !0, this._mainLayer.removeItem(i), t.Sounds.sfx.play("bonus_jump")) : 2 === i.itemType && (this._mainLayer.removeItem(i), t.Sounds.sfx.play("gold"), this._score += 1, this._scoreUI.score = this._score, this._scoreUI.bounce())
        }, i.prototype.emittDustLanding = function() {
            this._dustEmitter.emitX = this._player.x + 20, this._dustEmitter.emitY = this._player.y + 90, this._dustEmitter.setXSpeed(-100, 0), this._dustEmitter.explode(500, 2), this._dustEmitter.setXSpeed(0, 100), this._dustEmitter.explode(500, 2)
        }, i.prototype.emittDustRunning = function() {
            if ("run" === this._player.animName) {
                var t = Math.floor(this.game.time.time / 250);
                t > this._runCounter && (this._runCounter = t, this._dustEmitter.emitX = this._player.x, this._dustEmitter.emitY = this._player.y + 80, this._dustEmitter.setXSpeed(-100, 0), this._dustEmitter.emitParticle())
            }
        }, i.prototype.gameOver = function() {
            if (!this._gameOver) {
                this._gameOver = !0;
                var e = t.Preferences.instance,
                    i = Math.floor(this._player.x / 64);
                i > e.record && (e.record = i, e.save()), this.time.events.add(3e3, function() {
                    this.game.state.start("Menu")
                }, this)
            }
        }, i.prototype.onResize = function(t, e) {
            this._bg.resize()
        }, i.prototype.onPause = function() {
            this.game.paused = !0
        }, i.prototype.onResume = function() {
            this.game.paused = !1
        }, i
    }(Phaser.State);
    t.Play = e
}(JumperBoyAdventures || (JumperBoyAdventures = {}));